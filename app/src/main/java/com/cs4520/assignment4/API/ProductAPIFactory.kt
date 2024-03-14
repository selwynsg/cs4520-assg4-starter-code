package com.cs4520.assignment4.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductAPIFactory {
    companion object {
        private const val BASE_URL = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/"

        fun makeRetrofitService(): ProductAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductAPI::class.java)
        }
    }
}