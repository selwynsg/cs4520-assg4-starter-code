package com.cs4520.assignment4.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productListDao(): ProductListDao
    companion object {
        private var instance: ProductDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): ProductDatabase {

            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, ProductDatabase::class.java,
                    "products"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }
}

