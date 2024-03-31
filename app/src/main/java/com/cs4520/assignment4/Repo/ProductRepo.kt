package com.cs4520.assignment4.Repo

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cs4520.assignment4.API.ProductAPIFactory
import com.cs4520.assignment4.Database.Product
import com.cs4520.assignment4.Database.ProductDatabase
import com.cs4520.assignment4.Database.ProductListDao
import com.cs4520.assignment4.Worker.ProductWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class ProductRepo(application: Application) {
    private var productListDao: ProductListDao
    private val productAPI = ProductAPIFactory.makeRetrofitService()
    private val database = ProductDatabase.getInstance(application)


    init {
        productListDao = database.productListDao()
    }



    private suspend fun insertProducts(products: List<Product>) {
        productListDao.deleteAllProducts()
        products.forEach { product ->
            productListDao.insert(product)
        }
    }

    suspend fun fetchProductsFromAPI(page: Int? = null): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val response = productAPI.getProduct(page)
            if (response.isSuccessful && response.body() != null) {
                val products = response.body()!!.filterNot { it.name.isBlank() || it.type.isBlank() || it.price < 0 }
                insertProducts(products)
                Result.success(products)
            } else {
                throw Exception("Fetch was not successfull switching to database")
            }
        } catch (e: Exception) {
            val products = productListDao.getProductsList()
            if (products.isEmpty()) {
                Result.failure(e)
            } else {
                Result.success(products)
            }
        }
    }



}

