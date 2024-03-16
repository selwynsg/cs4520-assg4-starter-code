package com.cs4520.assignment4.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductListDao {
    @Query("SELECT * FROM products")
    fun getAll(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM products")
    suspend fun getProductsList(): List<Product>


}