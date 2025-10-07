package com.bioceuticamilano

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bioceuticamilano.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.tvTitle.text = "BioInfusion+ | Microinfusion System"
        binding.tvDescription.text = "Regain youthful, supple, and wrinkle-free skin in just a few applications at home. Guaranteed results or a full refund!"

        // Sample grid items for customer results
        val images = listOf(R.drawable.order_1, R.drawable.order_1, R.drawable.order_1, R.drawable.order_1)
        binding.gvResults.adapter = ResultsAdapter(this, images)

        // buttons no-op for now
        binding.btnAddToCart.setOnClickListener { /* TODO: implement */ }
        binding.btnBuyNow.setOnClickListener { /* TODO: implement */ }
    }
}
