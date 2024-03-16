package com.cs4520.assignment4.View
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.Database.Product
import com.cs4520.assignment4.R
import com.cs4520.assignment4.databinding.ProductItemBinding

class ProductViewAdapter(private val products: MutableList<Product>) :
    RecyclerView.Adapter<ProductViewAdapter.ProductViewHolder>() {
    class ProductViewHolder(binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val nameTextView: TextView = binding.productName
        private val expiryDateTextView: TextView = binding.productExpiryDate
        private val priceTextView: TextView = binding.productPrice
        private val productImageView: ImageView = binding.productImage

        fun bind(product: Product) {
            nameTextView.text = product.name
            priceTextView.text = itemView.context.getString(R.string.price, product.price.toString())
            if (product.expiryDate.isNullOrEmpty()) {
                expiryDateTextView.visibility = View.GONE
            } else {
                expiryDateTextView.visibility = View.VISIBLE
                expiryDateTextView.text = itemView.context.getString(R.string.expires,product.expiryDate)
            }

            val (backgroundColor, imageResId) = when (product.type) {
                 "Equipment" -> Pair("#E06666", R.drawable.hammer)
                 "Food" -> Pair("#FFD965", R.drawable.tomato)
                else -> {Pair("#FFFFFF", R.drawable.hammer)}
            }

            itemView.setBackgroundColor(Color.parseColor(backgroundColor))
            productImageView.setImageResource(imageResId)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(view, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
