package com.bioceuticamilano

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bioceuticamilano.adapters.ProductAdapter
import com.bioceuticamilano.adapters.TestimonialAdapter
import com.bioceuticamilano.adapters.Product
import com.bioceuticamilano.adapters.Testimonial
import com.bioceuticamilano.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // simple navigation from SignIn -> Home
        binding.header.setOnClickListener {
            // placeholder
        }

        // Profile click opens Notification screen as example
        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }

        // dummy data
        val products = listOf(
            Product("BioInfusion+ | Microinfusion System", "$79 USD", R.drawable.ic_product_placeholder),
            Product("BioInfusion+ | Microinfusion System", "$79 USD", R.drawable.ic_product_placeholder),
            Product("BioInfusion+ | Microinfusion System", "$79 USD", R.drawable.ic_product_placeholder)
        )

        val testimonials = listOf(
            Testimonial("David J.", "★ ★ ★ ★ ★ 5.0", "I decided to try Voyafly's eSIM service during my road trip across Australia, and I'm glad I did. The eSIM...", R.drawable.profile_image),
            Testimonial("Anna K.", "★ ★ ★ ★ ★ 5.0", "Excellent product quality and fast delivery.", R.drawable.profile_image)
        )

        binding.rvVideos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvVideos.adapter = ProductAdapter(products)

        binding.rvFeatured.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeatured.adapter = ProductAdapter(products)

        binding.rvTestimonials.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvTestimonials.adapter = TestimonialAdapter(testimonials)
    }
}
