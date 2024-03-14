package com.cs4520.assignment4.API


import com.cs4520.assignment4.Database.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductAPI {
    @GET("/prod")
    fun getProducts(@Query("page") pageNumber: Int? = null
    ): Response<List<Product>>
}