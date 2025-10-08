package com.bioceuticamilano

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bioceuticamilano.databinding.ActivityDetailBinding
import android.widget.ImageView

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTitle.text = "BioInfusion+ | Microinfusion System"
        binding.tvDescription.text = "Regain youthful, supple, and wrinkle-free skin in just a few applications at home. Guaranteed results or a full refund!"

        // Image carousel setup
        val carouselImages = listOf(R.drawable.order_1, R.drawable.order_2, R.drawable.order_3, R.drawable.order_2, R.drawable.order_3)
        binding.vpCarousel.adapter = CarouselAdapter(carouselImages)
        setupDots(carouselImages.size)
        binding.vpCarousel.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position)
            }
        })

        // Render Trustpilot rating dynamically
        renderTrustpilot(1.5f)

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

        // 10dp in pixels
        val sizePx = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP,
            20f,
            resources.displayMetrics
        ).toInt()
        val marginPx = (2 * resources.displayMetrics.density).toInt()

        for (i in 1..5) {
            val iv = android.widget.ImageView(this)
            val lp = android.widget.LinearLayout.LayoutParams(sizePx, sizePx)
            lp.setMargins(marginPx, 0, marginPx, 0)
            iv.layoutParams = lp
            iv.adjustViewBounds = true
            iv.scaleType = ImageView.ScaleType.CENTER_CROP
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

    private fun setupDots(count: Int) {
        binding.llDots.removeAllViews()
        val size = (15 * resources.displayMetrics.density).toInt()
        val margin = (6 * resources.displayMetrics.density).toInt()
        for (i in 0 until count) {
            val dot = ImageView(this)
            val lp = android.widget.LinearLayout.LayoutParams(size, size)
            lp.setMargins(margin, 10, margin, 10)
            dot.layoutParams = lp
            dot.setImageResource(if (i == 0) R.drawable.dot_active else R.drawable.dot_inactive)
            binding.llDots.addView(dot)
        }
    }

    private fun updateDots(activePos: Int) {
        for (i in 0 until binding.llDots.childCount) {
            val v = binding.llDots.getChildAt(i) as ImageView
            v.setImageResource(if (i == activePos) R.drawable.dot_active else R.drawable.dot_inactive)
        }
    }
}
