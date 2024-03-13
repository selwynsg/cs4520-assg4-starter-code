package com.cs4520.assignment4

sealed class Product() {
    abstract val name : String
    abstract val expiryDate : String?
    abstract val price: Int
    data class Equipment(override val name: String, override val expiryDate: String?, override val price: Int) : Product()
    data class Food(override val name: String, override val expiryDate: String?, override val price: Int) : Product()
}


fun parseData(dataset : List<List<Any?>>): List<Product>{
    return dataset.map { value ->
        when (value[1] as String) {
            "Equipment" -> Product.Equipment(value[0] as String, value[2] as? String, value[3] as Int)
            "Food" -> Product.Food(value[0] as String, value[2] as? String, value[3] as Int)
            else -> throw IllegalArgumentException("Not a valid input")
        }
    }
}

val listOfProducts = parseData(productsDataset)