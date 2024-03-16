package com.cs4520.assignment4.API

import com.cs4520.assignment4.API.Api.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductAPIFactory {
    companion object {


        fun makeRetrofitService(): ProductAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductAPI::class.java)
        }
    }
}