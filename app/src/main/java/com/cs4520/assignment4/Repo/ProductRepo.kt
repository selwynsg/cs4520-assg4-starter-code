package com.cs4520.assignment4.Repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.cs4520.assignment4.API.ProductAPIFactory
import com.cs4520.assignment4.Database.Product
import com.cs4520.assignment4.Database.ProductDatabase
import com.cs4520.assignment4.Database.ProductListDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepo(application: Application, private var coroutineScope: CoroutineScope) {
    private var allProducts: LiveData<List<Product>>
    private var productListDao: ProductListDao
    private val productAPI = ProductAPIFactory.makeRetrofitService()
    private val database = ProductDatabase.getInstance(application)

    init {
        productListDao = database.productListDao()
        allProducts = productListDao.getAll()
    }

    fun getProductsFromDatabase(): LiveData<List<Product>> {
        return allProducts
    }

    suspend fun insertProducts(products: List<Product>) {
        this.deleteDatabaseEntries()
        products.forEach { product ->
            productListDao.insert(product)
        }
    }

    private suspend fun deleteDatabaseEntries() {
        productListDao.deleteAllProducts()
    }
    suspend fun fetchProductsFromAPI(page: Int? = null, onResult: (Result<List<Product>>, List<Product>?) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val response = productAPI.getProduct(page)
                if (response.isSuccessful && response.body() != null) {
                    val products = response.body()!!.filterNot { it.name.isBlank() || it.type.isBlank() || it.price < 0 }
                    insertProducts(products)
                    onResult(Result.success(products), products)
                } else {
                    throw Exception("API call failed") // This will be caught by the catch block
                }
            } catch (e: Exception) {
                val products = getProductsFromDatabase().value
                if (!products.isNullOrEmpty()) {
                    onResult(Result.success(products), products)
                } else {
                    throw Exception("Everything is empty")
                }
            }
        }
    }

    }

