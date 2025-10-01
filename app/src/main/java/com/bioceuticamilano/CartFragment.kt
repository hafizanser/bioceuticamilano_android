package com.bioceuticamilano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bioceuticamilano.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // dummy data
        val items = mutableListOf(
            CartItem("Bioinlusion+ Sistema di Micro", "Save 20%", "€138.00", "€297.00", R.drawable.image_cart, 1),
            CartItem("Bioinlusion+ Sistema di Micro", "Save 20%", "€138.00", "€297.00", R.drawable.image_cart, 1)
        )

        adapter = CartAdapter(items)
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = adapter

        binding.notesHeader.setOnClickListener { toggleNotes() }
        binding.ivNotesArrow.setOnClickListener { toggleNotes() }

        binding.btnOrder.setOnClickListener {
            Toast.makeText(requireContext(), "Order placed (dummy)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleNotes() {
        val visible = binding.notesContent.visibility == View.VISIBLE
        binding.notesContent.visibility = if (visible) View.GONE else View.VISIBLE
        binding.ivNotesArrow.rotation = if (visible) 0f else 180f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
