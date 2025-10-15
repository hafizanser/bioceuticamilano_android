package com.bioceuticamilano.utils

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun View.applyInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        // Apply padding/margins as needed
        view.updatePadding(
            left = systemBars.left,
            right = systemBars.right,
            top = systemBars.top,
            bottom = systemBars.bottom
        )
        WindowInsetsCompat.CONSUMED
    }
}
