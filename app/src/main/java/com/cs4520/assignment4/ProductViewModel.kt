package com.cs4520.assignment4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment4.API.ProductAPIFactory
import com.cs4520.assignment4.Database.Product
import kotlinx.coroutines.launch

class ProductViewModel(app: Application) : AndroidViewModel(app) {
    private val productAPI = ProductAPIFactory.makeRetrofitService()
    private val repository = ProductRepo(app,productAPI)
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _loading


    private val _err = MutableLiveData<String>()
    val error: LiveData<String> = _err

    fun fetchProducts(page: Int? = null) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getProducts(page)
                if (response.isSuccessful && response.body() != null) {
                    val products = response.body()!!.filterNot { it.name.isBlank() || it.type.isBlank() || it.price <= 0 }
                    val no_dups = products.distinctBy { it.name }
                    _products.value = no_dups
                    repository.insertProducts(products)
                } else {
                    _err.value = "Failed to load products: ${response.message()}"
                }
            } catch (e: Exception) {
                _err.value = "Failed to load products: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }


}
