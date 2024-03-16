package com.cs4520.assignment4.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment4.Database.Product
import com.cs4520.assignment4.Repo.ProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepo: ProductRepo = ProductRepo(application,viewModelScope)
    private var _result = MutableLiveData<List<Product>>()
    val result: LiveData<List<Product>> = _result
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun fetchProducts(page: Int? = null) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.fetchProductsFromAPI(page) { result, _ ->
                _loading.postValue(false)
                if (result.isSuccess) {
                    _result.postValue(result.getOrNull())
                } else {
                    val errorMessage = result.exceptionOrNull()?.message ?: "Failed to fetch products from API"
                }
            }
        }
    }

}
