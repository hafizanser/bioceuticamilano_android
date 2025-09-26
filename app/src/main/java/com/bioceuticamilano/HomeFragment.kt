package com.bioceuticamilano

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bioceuticamilano.adapters.Product
import com.bioceuticamilano.adapters.ProductAdapter
import com.bioceuticamilano.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivProfile.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationActivity::class.java))
        }

        val products = listOf(
            Product("BioInfusion+ | Microinfusion System", "$79 USD", "$99 USD", R.drawable.ic_product_placeholder),
            Product("BioInfusion+ | Microinfusion System", "$79 USD", "$99 USD", R.drawable.ic_product_placeholder),
            Product("BioInfusion+ | Microinfusion System", "$79 USD", "$99 USD", R.drawable.ic_product_placeholder)
        )

        binding.rvVideos.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvVideos.adapter = ProductAdapter(products)

        binding.rvFeatured.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeatured.adapter = ProductAdapter(products)

        // add TestimonialsFragment inside the container
        childFragmentManager.beginTransaction()
            .replace(R.id.testimonialsContainer, TestimonialsFragment())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
