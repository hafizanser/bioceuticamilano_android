package com.bioceuticamilano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryProductsFragment : Fragment() {

    private lateinit var recycler: RecyclerView

    companion object {
        private const val ARG_TITLE = "arg_title"
        fun newInstance(title: String) = CategoryProductsFragment().apply {
            arguments = Bundle().apply { putString(ARG_TITLE, title) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_category_products, container, false)
        recycler = root.findViewById(R.id.rvProducts)

        val title = arguments?.getString(ARG_TITLE) ?: "Category"
        root.findViewById<TextView>(R.id.tvTitle).text = title

        // sample products
        val products = listOf(
            ProductItem("BioInfusion+ | Microinfusion System", "€79 EUR", R.drawable.ic_product_placeholder),
            ProductItem("BioInfusion+ | Microinfusion System", "€79 EUR", R.drawable.ic_product_placeholder),
            ProductItem("BioInfusion+ | Microinfusion System", "€79 EUR", R.drawable.ic_product_placeholder),
            ProductItem("BioInfusion+ | Microinfusion System", "€79 EUR", R.drawable.ic_product_placeholder)
        )

        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.adapter = ProductAdapter(products)

        return root
    }
}

data class ProductItem(val title: String, val price: String, val image: Int)
