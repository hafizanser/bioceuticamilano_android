package com.bioceuticamilano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bioceuticamilano.databinding.FragmentWishlistBinding

class WishlistFragment : Fragment() {
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: WishlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // dummy data
        val items = listOf(
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart),
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart),
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart),
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart),
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart),
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart),
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart),
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart),
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart),
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart),
            WishlistItem("BioInfusion + Sistema di Microinfusione", "$20", "$10", R.drawable.image_cart)
        )

        adapter = WishlistAdapter(items)
        binding.rvWishlist.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWishlist.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
