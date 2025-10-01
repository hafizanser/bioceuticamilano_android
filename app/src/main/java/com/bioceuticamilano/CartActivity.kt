package com.bioceuticamilano

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bioceuticamilano.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        // dummy data
        val items = mutableListOf(
            CartItem("Bioinlusion+ Sistema di Micro", "Save 20%", "€138.00", "€297.00", R.drawable.image_chart, 1),
            CartItem("Bioinlusion+ Sistema di Micro", "Save 20%", "€138.00", "€297.00", R.drawable.image_chart, 1)
        )

        adapter = CartAdapter(items)
        binding.rvCart.layoutManager = LinearLayoutManager(this)
        binding.rvCart.adapter = adapter

        // expandable notes
        binding.notesHeader.setOnClickListener {
            toggleNotes()
        }

        binding.ivNotesArrow.setOnClickListener {
            toggleNotes()
        }

        // order button
        binding.btnOrder.setOnClickListener {
            Toast.makeText(this, "Order placed (dummy)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleNotes() {
        val visible = binding.notesContent.visibility == View.VISIBLE
        binding.notesContent.visibility = if (visible) View.GONE else View.VISIBLE
        binding.ivNotesArrow.rotation = if (visible) 0f else 180f
    }
}

// simple data class for cart items
data class CartItem(
    val title: String,
    val badge: String,
    val price: String,
    val oldPrice: String,
    val thumbRes: Int,
    var qty: Int
)
