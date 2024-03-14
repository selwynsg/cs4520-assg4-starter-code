package com.cs4520.assignment4

import android.app.Application
import com.cs4520.assignment4.API.ProductAPI
import com.cs4520.assignment4.Database.Product
import com.cs4520.assignment4.Database.ProductDatabase
import com.cs4520.assignment4.Database.ProductListDao
import retrofit2.Response

class ProductRepo(application: Application,private val productAPI: ProductAPI) {
    private var productListDao: ProductListDao
    private val database = ProductDatabase.getInstance(application)
    init {
        productListDao = database.productListDao()
    }


    suspend fun getProducts(page: Int? = null): Response<List<Product>> = productAPI.getProducts(page)

    suspend fun insertProducts(products: List<Product>) {
        products.forEach { product ->
                productListDao.insert(product)
            }
        }
    }
