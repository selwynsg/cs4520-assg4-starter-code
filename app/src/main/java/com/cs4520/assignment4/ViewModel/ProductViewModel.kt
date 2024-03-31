package com.cs4520.assignment4.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cs4520.assignment4.Database.Product
import com.cs4520.assignment4.Repo.ProductRepo
import com.cs4520.assignment4.Worker.ProductWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepo: ProductRepo = ProductRepo(application)
    private var _result = MutableLiveData<List<Product>>()
    val result: LiveData<List<Product>> = _result
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    private val workManager = WorkManager.getInstance(application)

    init{
        scheduleProductWorker()
    }
    private fun scheduleProductWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<ProductWorker>(repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.HOURS).setConstraints(constraints).build()
        workManager.enqueueUniquePeriodicWork("UpdateProduct",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest)

    }

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

class MyViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
