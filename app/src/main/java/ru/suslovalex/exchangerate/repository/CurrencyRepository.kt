package ru.suslovalex.exchangerate.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.suslovalex.exchangerate.data.DataCurrency
import ru.suslovalex.exchangerate.data.entity.CurrencyEntity
import ru.suslovalex.exchangerate.db.CurrencyDatabase
import ru.suslovalex.exchangerate.model.CurrencyResponse
import ru.suslovalex.exchangerate.retrofit.RemoteDataStore

class CurrencyRepository(
    private val remoteDataStore: RemoteDataStore,
    private val currencyDatabase: CurrencyDatabase
) {

    private val currencyDao = currencyDatabase.currencyDao


    lateinit var date: String

    suspend fun getData(): List<DataCurrency> = withContext(Dispatchers.IO) {

        val data = remoteDataStore.getData()
        date = convertDate(data)

        Log.d("DATA", date)

        val valute = data.valute
        val valuteStrings = valute.toString()
            .drop(11)
            .dropLast(2)
            .split("\\),\\s\\w{3}=".toRegex())
            .map { it.drop(4) }

        for (string in valuteStrings) {
            Log.d("DATA", string)
        }

        val valuteMap = valuteStrings.map {
            it.split(", ").associate {
                val (left, right) = it.split("=")
                left to right
            }
        }

        val dataCurrencyList = valuteMap.map {
            DataCurrency(
                iD = it["iD"].toString(),
                numCode = it["numCode"].toString(),
                charCode = it["charCode"].toString(),
                nominal = it["nominal"]?.toInt() ?: 0,
                name = it["name"].toString(),
                value = it["value"]?.toDouble() ?: 0.0,
                previous = it["previous"]?.toDouble() ?: 0.0
            )
        }

        for (string in dataCurrencyList) {
            Log.d("DATA", "${string.iD} || ${string.name} || ${string.charCode}")
        }

        return@withContext dataCurrencyList

    }

    private fun convertDate(data: CurrencyResponse): String {
        return data.date.split(":")[0].dropLast(3)
    }

    suspend fun saveDataCurrencyToDatabase(listDataCurrency: List<DataCurrency>) =
        withContext(Dispatchers.IO) {

            val listCurrencyEntity = listDataCurrency.map { dataCurrency ->
                CurrencyEntity(
                    id = dataCurrency.iD,
                    numCode = dataCurrency.numCode,
                    charCode = dataCurrency.charCode,
                    nominal = dataCurrency.nominal,
                    name = dataCurrency.name,
                    value = dataCurrency.value,
                    previous = dataCurrency.previous,
                    date = date
                )
            }
            currencyDao.insertCurrencies(listCurrencyEntity)

        }

    suspend fun removeDatabase() = withContext(Dispatchers.IO){
        return@withContext currencyDao.deleteTableCurrencies()
    }

    suspend fun readDataFromDatabase(): List<CurrencyEntity> = withContext(Dispatchers.IO){
        currencyDao.getCurrencies()
    }


}
