package ru.suslovalex.exchangerate

import android.app.Application
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.suslovalex.exchangerate.data.DataChecker
import ru.suslovalex.exchangerate.db.CurrencyDatabase
import ru.suslovalex.exchangerate.repository.CurrencyRepository
import ru.suslovalex.exchangerate.retrofit.RemoteDataStore
import ru.suslovalex.exchangerate.viewmodel.CurrencyConverterViewModel
import ru.suslovalex.exchangerate.viewmodel.CurrencyListViewModel
import ru.suslovalex.exchangerate.worker.CurrencyWorker
import java.util.concurrent.TimeUnit

const val PERIODIC_INTERVAL: Long = 12

class App: Application() {

    @KoinApiExtension
    override fun onCreate() {
        super.onCreate()

        periodicWork()

        val currencyListViewModelModule = module {
            viewModel { CurrencyListViewModel(get(), get()) }
        }

        val currencyConverterViewModel = module {
            viewModel { CurrencyConverterViewModel() }
        }

        val appModule = module {
            single { RemoteDataStore() }
            single { CurrencyDatabase(get()) }
            single { CurrencyRepository(get(), get()) }
            factory { DataChecker() }
        }

        startKoin {
            androidContext(this@App)
            modules(currencyListViewModelModule, currencyConverterViewModel, appModule)

        }
    }

    @KoinApiExtension
    private fun periodicWork() {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(CurrencyWorker::class.java, PERIODIC_INTERVAL, TimeUnit.HOURS)
                .setConstraints(constraint)
                .build()

        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)
    }
}