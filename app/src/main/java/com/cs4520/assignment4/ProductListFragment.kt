package com.cs4520.assignment4;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.cs4520.assignment4.databinding.ProductListFragmentBinding


class ProductListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = ProductListFragmentBinding.inflate(layoutInflater)

        binding.myRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.myRecyclerView.adapter = ProductViewAdapter(listOfProducts)
        return binding.root
    }

}
