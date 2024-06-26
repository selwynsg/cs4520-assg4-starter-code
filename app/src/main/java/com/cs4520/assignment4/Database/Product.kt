package com.cs4520.assignment4.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
     val name : String,
     val type: String,
     val expiryDate : String?,
     val price: Double,
     @PrimaryKey(autoGenerate = true) val id: Int? = null
)


