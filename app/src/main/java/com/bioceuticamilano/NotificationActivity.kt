package com.bioceuticamilano

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.bioceuticamilano.databinding.ActivityNotificationsBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        binding.openSettings.setOnClickListener {
            // Try to open the app notification settings. Fallback to app details if not available.
            val intent = Intent().apply {
                action = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Settings.ACTION_APP_NOTIFICATION_SETTINGS
                } else {
                    "android.settings.APP_NOTIFICATION_SETTINGS"
                }
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                putExtra("app_package", packageName)
                putExtra("app_uid", applicationInfo.uid)
            }
            try {
                startActivity(intent)
            } catch (e: Exception) {
                val fallback = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(fallback)
            }
        }
    }
}
