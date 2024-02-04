package com.earl.retrofitexample

import com.earl.retrofitexample.data.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()


    //If we use another api we just initialize another instance
    val api: Api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())//this convert json body to dataclass
        .baseUrl(Api.BASE_URL)
        .client(client)
        .build()
        .create(Api::class.java)
}