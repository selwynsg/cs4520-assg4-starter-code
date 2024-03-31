package com.cs4520.assignment4.Worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cs4520.assignment4.Repo.ProductRepo

class ProductWorker(ctx: Context, params: WorkerParameters, private val productRepo: ProductRepo) : CoroutineWorker(ctx, params)  {
    override suspend fun doWork(): Result {
        return try {
            productRepo.fetchProductsFromAPI(null)
            Result.success()
        } catch (e :Exception){
            Result.failure()
        }
    }



}