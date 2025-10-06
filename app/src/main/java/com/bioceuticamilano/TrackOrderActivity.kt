package com.bioceuticamilano

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bioceuticamilano.databinding.ActivityTrackOrderBinding

class TrackOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrackOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        binding.btnTrack.setOnClickListener {
            // open courier tracking URL if available
            val courierId = binding.tvCourierId.text.toString()
            if (courierId.isNotEmpty()) {
                val url = "https://www.example.com/track/$courierId"
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(i)
            } else {
                Toast.makeText(this, "No tracking id", Toast.LENGTH_SHORT).show()
            }
        }

//        binding.btnCallNow.setOnClickListener {
//            val phone = binding.tvCustomerPhone.text.toString().replace(" ", "")
//            val intent = Intent(Intent.ACTION_DIAL)
//            intent.data = Uri.parse("tel:$phone")
//            startActivity(intent)
//        }
//
//        binding.btnEmail.setOnClickListener {
//            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:contact@bioceuticamilano.com"))
//            startActivity(emailIntent)
//        }

        // default step, 0..3 (0 = ordered, 1 = shipped, 2 = out for delivery, 3 = arriving)
        setStep(2)
    }

    private fun setStep(step: Int) {
        // dots and lines views
        val dots = listOf(binding.dot1, binding.dot2, binding.dot3, binding.dot4)
        val lines = listOf(binding.line1, binding.line2, binding.line3)
        val texts = listOf<TextView>(
            binding.tvStep1,
            binding.tvStep2,
            binding.tvStep3,
            binding.tvStep4
        )

        val activeColor = ContextCompat.getColor(this, R.color.track_active)
        val inactiveColor = ContextCompat.getColor(this, R.color.track_inactive)
        val activeLineColor = ContextCompat.getColor(this, R.color.track_line_active)
        val inactiveLineColor = ContextCompat.getColor(this, R.color.track_line_inactive)

        for (i in dots.indices) {
            val dot = dots[i]
            val text = texts[i]
            if (i <= step) {
                dot.setBackgroundResource(R.drawable.track_dot_active)
                text.setTextColor(activeColor)
            } else {
                dot.setBackgroundResource(R.drawable.track_dot_inactive)
                text.setTextColor(inactiveColor)
            }
        }

        for (i in lines.indices) {
            val line = lines[i]
            if (i < step) {
                line.setBackgroundColor(activeLineColor)
            } else {
                line.setBackgroundColor(inactiveLineColor)
            }
        }
    }
}
