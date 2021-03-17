package ru.suslovalex.exchangerate.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.suslovalex.exchangerate.data.DataCurrency
import ru.suslovalex.exchangerate.repository.CurrencyRepository

@KoinApiExtension
class CurrencyWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams), KoinComponent {

    private val currencyRepository: CurrencyRepository by inject()

    override suspend fun doWork(): Result {
        return try {
            val listDataCurrency = currencyRepository.getData()
            saveData(listDataCurrency)
            Result.success()
        }
        catch (error: Throwable){
            Result.failure()
        }
    }

    private suspend fun saveData(listDataCurrency: List<DataCurrency>) {
        currencyRepository.saveDataCurrencyToDatabase(listDataCurrency)
    }
}