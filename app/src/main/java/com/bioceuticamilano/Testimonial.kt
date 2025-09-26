package com.bioceuticamilano

import androidx.annotation.DrawableRes

data class Testimonial(
    val name: String,
    val rating: Float,
    val comment: String,
    @DrawableRes val avatarRes: Int
)
