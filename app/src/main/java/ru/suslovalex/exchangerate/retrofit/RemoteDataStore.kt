package ru.suslovalex.exchangerate.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.suslovalex.exchangerate.model.CurrencyResponse

class RemoteDataStore {

    private val currencyApi: CurrencyApi by lazy {
        retrofit.create(CurrencyApi::class.java)
    }

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(CurrencyApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
                )
                .build()
        }

    suspend fun getData(): CurrencyResponse {
        return currencyApi.getData()
    }
}