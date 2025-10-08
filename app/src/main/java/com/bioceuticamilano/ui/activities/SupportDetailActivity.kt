package com.bioceuticamilano.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bioceuticamilano.R
import com.bioceuticamilano.databinding.ActivitySupportDetailBinding

class SupportDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupportDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // default back arrow: rely on toolbar in layout
        binding.ivBack.setOnClickListener { finish() }

        // get type
        val type = intent.getStringExtra("support_type") ?: "problems"

        when (type) {
            "problems" -> setupProblems()
            "where" -> setupWhere()
            "unsatisfied" -> setupUnsatisfied()
            "product_info" -> setupProductInfo()
            "other" -> setupOther()
            else -> setupProblems()
        }
    }

    private fun setupProblems() {
        binding.tvTitle.text = "Problems with the order"
        binding.ivIcon.setImageResource(R.drawable.ic_info_detail)
        binding.tvBody.text = "If your package is damaged, you can request a replacement or a full refund. If you wish to cancel or change your order, you can also contact us at the following numbers:"
        // show customer service card
//        binding.customerCard.visibility = View.VISIBLE
    }

    private fun setupWhere() {
        binding.tvTitle.text = "Where is my package?"
        binding.ivIcon.setImageResource(R.drawable.ic_question_detail)
        binding.tvBody.text = "Please note: Due to the transition from BRT to Poste Italiane, our packages may experience delays of up to 10 days from the expected delivery date. We apologize for the inconvenience. If you require additional information or the above deadline has passed, please contact us using one of these channels:"
//        binding.customerCard.visibility = View.GONE
    }

    private fun setupUnsatisfied() {
        binding.tvTitle.text = "I'm not satisfied"
        binding.ivIcon.setImageResource(R.drawable.ic_unsatisfied_detail)
        binding.tvBody.text = "We remind you that you can try our products for up to 90 days and get a full refund, even if you use them. If you haven't finished your treatment, we recommend completing the cycle before contacting us. Depending on your skin type, results may require multiple applications. If you would like to request a refund, please use the following channels:"
//        binding.customerCard.visibility = View.GONE
    }

    private fun setupProductInfo() {
        binding.tvTitle.text = "Product information"
        binding.ivIcon.setImageResource(R.drawable.ic_info_detail)
        binding.tvBody.text = "If you have any questions about our products, our consultants are available on our toll-free number to answer your questions:"
//        binding.customerCard.visibility = View.GONE
    }

    private fun setupOther() {
        binding.tvTitle.text = "Other"
        binding.ivIcon.setImageResource(R.drawable.ic_question_detail)
        binding.tvBody.text = "For any inquiries, you can contact us and receive a response within 48 hours via our email address or toll-free number below:"
//        binding.customerCard.visibility = View.GONE
    }
}
