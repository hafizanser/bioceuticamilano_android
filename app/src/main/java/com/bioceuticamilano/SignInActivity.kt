package com.bioceuticamilano

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val root = findViewById<View>(R.id.signin_root)
        // save original padding
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
    }
}
