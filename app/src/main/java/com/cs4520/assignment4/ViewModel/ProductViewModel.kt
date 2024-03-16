package com.cs4520.assignment4.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment4.Database.Product
import com.cs4520.assignment4.Repo.ProductRepo
import kotlinx.coroutines.launch


class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepo: ProductRepo = ProductRepo(application)
    private var _result = MutableLiveData<List<Product>>()
    val result: LiveData<List<Product>> = _result
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchProducts(page: Int? = null) {
        _loading.value = true
        viewModelScope.launch {
            val fetchResult = productRepo.fetchProductsFromAPI(page)
            _loading.postValue(false)
            fetchResult.onSuccess { products ->
                _result.postValue(products)
                _error.postValue(null)
            }.onFailure { exception ->
                _error.postValue(exception.message)
            }
        }
    }
}