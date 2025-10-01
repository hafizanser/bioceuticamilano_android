package com.bioceuticamilano

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bioceuticamilano.databinding.ActivityProfileDetailBinding

class ProfileDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back
        binding.ivBack.setOnClickListener { finish() }

        // sample content
        binding.tvHeader.text = "Personal Info"
        binding.tvName.text = "Vishal Khadok"
        binding.tvSub.text = "sdklfjdskf akdfjj dskfjdk"

        // edit button opens EditProfileActivity
        binding.tvEdit.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
    }
}
