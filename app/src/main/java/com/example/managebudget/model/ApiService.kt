package com.example.managebudget.model

import android.annotation.SuppressLint
import com.example.managebudget.data.CryptoResponse
import com.example.managebudget.data.DollarResponse
import com.example.managebudget.utils.API_KEY
import com.example.managebudget.utils.CRYPTO_URL
import com.example.managebudget.utils.DOLLAR_URL
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url
interface ApiServiceCrypto{

    @GET("data/top/totalvolfull")
    suspend fun getCryptoData( @Query("tsym") targetSymbol: String, @Query("limit") limitNumber: String  ) : CryptoResponse
}
interface ApiServiceDollar{
    @GET("margani/pricedb/main/tgju/current/price_dollar_rl/latest.json")
    suspend fun getDollarData(): DollarResponse

}

    @SuppressLint("SuspiciousIndentation")
    fun createApiServiceCrypto(): ApiServiceCrypto {

        val okHttpClient1 = OkHttpClient.Builder()

            .addInterceptor {
                val oldReq = it.request()
                val newReq = oldReq.newBuilder()
                    newReq.addHeader("Authorization" , "Bearer ${API_KEY}")
                newReq.method(oldReq.method, oldReq.body)
                return@addInterceptor it.proceed(newReq.build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CRYPTO_URL)
            .client(okHttpClient1)
            .build()
        return retrofit.create(ApiServiceCrypto::class.java)
    }
fun createApiServiceDollar(): ApiServiceDollar {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(DOLLAR_URL)
            .build()
        return retrofit.create(ApiServiceDollar::class.java)
    }

