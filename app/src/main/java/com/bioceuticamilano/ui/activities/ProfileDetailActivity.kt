package com.bioceuticamilano.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bioceuticamilano.databinding.ActivityProfileDetailBinding
import com.bioceuticamilano.utils.Preferences

class ProfileDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back
        binding.ivBack.setOnClickListener { finish() }

        val userModel = Preferences.getUserDetails(this)

        // sample content
        binding.tvHeader.text = "Personal Info"
        binding.tvName.text = userModel.fullName
        binding.tvSub.text = userModel.email
        binding.tvFullName.text = userModel.fullName
        binding.tvEmailVal.text = userModel.email

        // edit button opens EditProfileActivity
        binding.tvEdit.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        val userModel = Preferences.getUserDetails(this)
        binding.tvName.text = userModel.fullName
        binding.tvSub.text = userModel.email
        binding.tvFullName.text = userModel.fullName
        binding.tvEmailVal.text = userModel.email

    }
}
