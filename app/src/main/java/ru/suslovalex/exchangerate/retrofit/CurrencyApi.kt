package ru.suslovalex.exchangerate.retrofit

import retrofit2.http.GET
import ru.suslovalex.exchangerate.model.CurrencyResponse

interface CurrencyApi {

    companion object{
        const val BASE_URL = "https://www.cbr-xml-daily.ru/"
    }

    @GET("daily_json.js")
    suspend fun getData(): CurrencyResponse
}