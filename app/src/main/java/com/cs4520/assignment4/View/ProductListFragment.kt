package com.cs4520.assignment4.View

import com.cs4520.assignment4.ViewModel.ProductViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs4520.assignment4.databinding.ProductListFragmentBinding

class ProductListFragment : Fragment() {
    private var _binding: ProductListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : ProductViewModel

    private lateinit var adapter: ProductViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductViewAdapter(mutableListOf())
        binding.myRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.myRecyclerView.adapter = adapter
        viewModel =  ViewModelProvider(this)[ProductViewModel::class.java]
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.fetchProducts()
        viewModel.result.observe(viewLifecycleOwner) { products ->
                if (products.isEmpty()) {
                    binding.myRecyclerView.visibility = View.GONE
                    binding.noProductsTextView.visibility = View.VISIBLE
                } else {
                    binding.myRecyclerView.visibility = View.VISIBLE
                    binding.noProductsTextView.visibility = View.GONE
                    adapter.updateProducts(products)
                }
            }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
