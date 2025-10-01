package com.bioceuticamilano

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bioceuticamilano.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        // prefill
        binding.etFullName.setText("Vishal Khadok")
        binding.etEmail.setText("bioceuticalsmilano@gmail.com")

        binding.btnSave.setOnClickListener {
            // TODO save data
            finish()
        }
    }
}
