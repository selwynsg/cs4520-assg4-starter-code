package com.cs4520.assignment4.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductListDao {
    @Query("SELECT * FROM products")
    fun getAll(): LiveData<List<Product>>

    @Insert
    fun insert(product: Product)
}