package com.cs4520.assignment4;

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs4520.assignment4.Database.Product
import com.cs4520.assignment4.databinding.ProductListFragmentBinding


class ProductListFragment : Fragment() {
    private var _binding: ProductListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : ProductViewModel
    private lateinit var adapter : ProductViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = ProductListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                val emptyProductList: MutableList<Product> = mutableListOf()
        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        adapter = ProductViewAdapter(emptyProductList)
        binding.myRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.myRecyclerView.adapter = adapter
        viewModel.products.observe(viewLifecycleOwner) { products -> adapter.updateProducts(products)}
        viewModel.fetchProducts()
        Log.v("myactivity","oncreateview in productlistfragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
