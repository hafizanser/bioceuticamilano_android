package com.bioceuticamilano

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Use Android 12+ splash API if available for smooth transition
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        // keep splash for 1.2 seconds then go to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }, 1200)
    }
}
