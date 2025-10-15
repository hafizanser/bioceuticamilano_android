package com.bioceuticamilano.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.bioceuticamilano.R
import com.bioceuticamilano.databinding.ActivityMainBinding
import com.bioceuticamilano.ui.fragments.ProfileFragment
import com.bioceuticamilano.ui.fragments.WishlistFragment
import com.bioceuticamilano.ui.fragments.BrowseFragment
import com.bioceuticamilano.ui.fragments.CartFragment
import com.bioceuticamilano.ui.fragments.HomeFragment
import com.bioceuticamilano.utils.applyInsets

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // edge-to-edge padding handling
        binding.root.applyInsets()

        // Load default fragment only once
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.host_fragment, HomeFragment())
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> supportFragmentManager.commit { replace(R.id.host_fragment,
                    HomeFragment()
                ) }
                R.id.nav_browse -> supportFragmentManager.commit { replace(R.id.host_fragment,
                    BrowseFragment()
                ) }
                R.id.nav_cart -> supportFragmentManager.commit { replace(R.id.host_fragment,
                    CartFragment()
                ) }
                R.id.nav_wishlist -> supportFragmentManager.commit { replace(R.id.host_fragment,
                    WishlistFragment()
                ) }
                R.id.nav_account -> supportFragmentManager.commit { replace(R.id.host_fragment,
                    ProfileFragment()
                ) }
            }
            true
        }
    }

    override fun onBackPressed() {
        // Handle back press to show bottom nav appropriately
        val fragment = supportFragmentManager.findFragmentById(R.id.host_fragment)
        if (fragment is BrowseFragment) {
            // If the current fragment is BrowseFragment, just pop the fragment
            supportFragmentManager.popBackStack()
        } else {
            // For other fragments, we can either pop the back stack or exit the app
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }
}