package com.bioceuticamilano.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bioceuticamilano.R
import com.bioceuticamilano.adapters.OrderHistoryAdapter
import com.bioceuticamilano.databinding.ActivityOrderHistoryBinding

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var adapter: OrderHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        // dummy data
        val items = listOf(
            OrderHistoryItem("94177012893", 3, "$700", "On the way 29 May", listOf(
                R.drawable.order_1, R.drawable.order_2, R.drawable.order_3, R.drawable.order_1
            )),
            OrderHistoryItem("94177012894", 2, "$420", "Delivered 10 Apr", listOf(
                R.drawable.order_1, R.drawable.order_2
            )),
            OrderHistoryItem("94177012895", 1, "$250", "On the way 01 Jun", listOf(
                R.drawable.order_1, R.drawable.order_2, R.drawable.order_3
            ))
        )

        adapter = OrderHistoryAdapter(items)
        binding.rvOrders.layoutManager = LinearLayoutManager(this)
        binding.rvOrders.adapter = adapter
    }
}

// simple data class
data class OrderHistoryItem(
    val orderNumber: String,
    val articlesCount: Int,
    val price: String,
    val shippingStatus: String,
    val thumbnails: List<Int>
)
