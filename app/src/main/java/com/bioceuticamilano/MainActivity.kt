package com.bioceuticamilano

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.bioceuticamilano.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // edge-to-edge padding handling
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load default fragment only once
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.host_fragment, HomeFragment())
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> supportFragmentManager.commit { replace(R.id.host_fragment, HomeFragment()) }
                R.id.nav_browse -> supportFragmentManager.commit { replace(R.id.host_fragment, HomeFragment()) }
                R.id.nav_cart -> {}
                R.id.nav_wishlist -> supportFragmentManager.commit { replace(R.id.host_fragment, HomeFragment()) }
                R.id.nav_account -> supportFragmentManager.commit { replace(R.id.host_fragment, ProfileFragment()) }
            }
            true
        }
    }
}