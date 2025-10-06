package com.bioceuticamilano

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bioceuticamilano.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var adapter: OrderDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        // dummy order items
        val items = listOf(
            OrderDetailItem(R.drawable.order_1, "BioActive+ Activating Day Cream", 1, "€34.90"),
            OrderDetailItem(R.drawable.order_1, "BioRegen+ Regenerating Night Cream", 1, "€34.90"),
            OrderDetailItem(R.drawable.order_1, "BioRegen+ Post-Microinfusion Hyaluronic Acid Serum", 1, "€34.90")
        )

        adapter = OrderDetailAdapter(items)
        binding.rvOrderItems.layoutManager = LinearLayoutManager(this)
        binding.rvOrderItems.adapter = adapter

        // read incoming extras (if any) from OrderHistoryAdapter
        val orderNumber = intent.getStringExtra("order_number") ?: "Order #3265"
        val orderStatus = intent.getStringExtra("order_status") ?: "Delivered"
        val orderTotal = intent.getStringExtra("order_total") ?: "€74.96"

        binding.tvOrderNumber.text = orderNumber
        binding.tvOrderDate.text = "$orderStatus • $orderTotal"

        // populate summary values
        binding.tvSubtotal.text = "€119,70"
        binding.tvDiscount.text = "-€119,70"
        binding.tvShipping.text = "Gratis"
        binding.tvTotalAmount.text = orderTotal
        binding.tvSummaryTotal.text = orderTotal

        var expanded = true
        binding.tvSummaryToggle.setOnClickListener {
            expanded = !expanded
            binding.llSummaryRows.visibility = if (expanded) View.VISIBLE else View.GONE
            binding.tvSummaryToggle.text = if (expanded) "Hide order summar" else "Show order summar"
        }

        binding.btnBuyAgain.setOnClickListener {
            Toast.makeText(this, "Buy again (dummy)", Toast.LENGTH_SHORT).show()
        }

        // tracking link click (placeholder)
        binding.tvTrackingNumber.setOnClickListener {
            Toast.makeText(this, "Open tracking (dummy)", Toast.LENGTH_SHORT).show()
        }
    }
}

// data class and adapter item model
data class OrderDetailItem(
    val thumbRes: Int,
    val title: String,
    val qty: Int,
    val price: String
)
