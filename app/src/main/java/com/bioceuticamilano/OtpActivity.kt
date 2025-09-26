package com.bioceuticamilano

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bioceuticamilano.databinding.ActivityOtpBinding
import com.bioceuticamilano.utils.Utility

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var otp5: EditText
    private lateinit var otp6: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        otp1 = binding.otp1
        otp2 = binding.otp2
        otp3 = binding.otp3
        otp4 = binding.otp4
        otp5 = binding.otp5
        otp6 = binding.otp6

        binding.btnBack.setOnClickListener { finish() }
        binding.resendOtp.setOnClickListener { Toast.makeText(this, "Code Send!", Toast.LENGTH_SHORT).show() }

        setupOtpAutoAdvance()

        // Handle continue button: validate entered 6-digit OTP and go to MainActivity
        binding.otpContinue.setOnClickListener {
            val code = listOf(
                binding.otp1.text?.toString().orEmpty(),
                binding.otp2.text?.toString().orEmpty(),
                binding.otp3.text?.toString().orEmpty(),
                binding.otp4.text?.toString().orEmpty(),
                binding.otp5.text?.toString().orEmpty(),
                binding.otp6.text?.toString().orEmpty()
            ).joinToString(separator = "")

            if (code.length != 6 || code.any { !it.isDigit() }) {
                Utility.showDialog(this, "Please enter a valid 6-digit code")
                return@setOnClickListener
            }

            // TODO: verify OTP with server if needed. For now, proceed to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupOtpAutoAdvance() {
        val fields = listOf(otp1, otp2, otp3, otp4, otp5, otp6)
        fields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // enable continue button when all fields are filled
                    val allFilled = fields.all { it.text?.length == 1 }
                    binding.otpContinue.isEnabled = allFilled
                    binding.otpContinue.alpha = if (allFilled) 1f else 0.5f
                }
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (index < fields.size - 1) fields[index + 1].requestFocus()
                    } else if (s?.isEmpty() == true) {
                        if (index > 0) fields[index - 1].requestFocus()
                    }
                }
            })
        }
    }
}
