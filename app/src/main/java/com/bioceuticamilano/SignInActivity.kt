package com.bioceuticamilano

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bioceuticamilano.databinding.ActivitySigninBinding
import com.bioceuticamilano.utils.Utility

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // save original padding
        val root = binding.signinRoot
        val origLeft = root.paddingLeft
        val origTop = root.paddingTop
        val origRight = root.paddingRight
        val origBottom = root.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                origLeft + sys.left,
                origTop + sys.top,
                origRight + sys.right,
                origBottom + sys.bottom
            )
            insets
        }

        setListeners()
    }

    private fun setListeners() {
        binding.email.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s?.toString().orEmpty().trim()
                val enabled = text.isNotEmpty()
                binding.btnContinue.isEnabled = enabled
                binding.btnContinue.alpha = if (enabled) 1f else 0.5f
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        binding.btnContinue.setOnClickListener {
            val emailStr = binding.email.text?.toString()?.trim().orEmpty()
            if (isValidEmail(emailStr)) {
                // proceed to OtpActivity (pass email)
                val intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("email", emailStr)
                startActivity(intent)
            } else {
                Utility.showDialog(this, "Please enter a valid email")
            }
        }

        binding.guest.setOnClickListener {
            // continue as guest
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
