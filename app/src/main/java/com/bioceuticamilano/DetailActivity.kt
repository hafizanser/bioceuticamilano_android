package com.bioceuticamilano

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bioceuticamilano.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTitle.text = "BioInfusion+ | Microinfusion System"
        binding.tvDescription.text = "Regain youthful, supple, and wrinkle-free skin in just a few applications at home. Guaranteed results or a full refund!"

        // Render Trustpilot rating dynamically
        renderTrustpilot(2.5f)

        // Sample grid items for customer results
        val images = listOf(R.drawable.order_1, R.drawable.order_1, R.drawable.order_1, R.drawable.order_1)
        binding.gvResults.adapter = ResultsAdapter(this, images)

        // Style chips
        binding.chipVegan.setBackgroundResource(R.drawable.bg_chip_green)
        binding.chipDerm.setBackgroundResource(R.drawable.bg_chip_blue)

        // buttons no-op for now
        binding.btnAddToCart.setOnClickListener { /* TODO: implement */ }
        binding.btnBuyNow.setOnClickListener { /* TODO: implement */ }
    }

    private fun renderTrustpilot(rating: Float) {
        // ratingStars is a horizontal LinearLayout. Show 5 star icons with partial fill depending on rating.
        val container = binding.ratingStars
        container.removeAllViews()
        val fullStars = rating.toInt()
        val hasHalf = (rating - fullStars) >= 0.5f
        for (i in 1..5) {
            val iv = android.widget.ImageView(this)
            val lp = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(2, 0, 2, 0)
            iv.layoutParams = lp
            iv.adjustViewBounds = true
            iv.scaleType = android.widget.ImageView.ScaleType.CENTER_INSIDE
            val drawable = when {
                i <= fullStars -> ContextCompat.getDrawable(this, R.drawable.trust_star_full)
                i == fullStars + 1 && hasHalf -> ContextCompat.getDrawable(this, R.drawable.trust_star_half)
                else -> ContextCompat.getDrawable(this, R.drawable.ic_star_outline)
            }
            iv.setImageDrawable(drawable)
            container.addView(iv)
        }
        binding.tvRatingText.text = String.format("(%.1f Excellent)", rating)
    }
}
