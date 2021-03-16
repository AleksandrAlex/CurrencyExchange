package ru.suslovalex.exchangerate

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.suslovalex.exchangerate.data.DataChecker
import ru.suslovalex.exchangerate.repository.CurrencyRepository
import ru.suslovalex.exchangerate.retrofit.RemoteDataStore
import ru.suslovalex.exchangerate.viewmodel.CurrencyListViewModel

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        val currencyListViewModelModule = module {
            viewModel { CurrencyListViewModel(get(), get()) }
        }

        val appModule = module {
            single { RemoteDataStore() }
            single { CurrencyRepository(get()) }
            factory { DataChecker() }
        }

        startKoin {
            androidContext(this@App)
            modules(currencyListViewModelModule, appModule)

        }
    }

}