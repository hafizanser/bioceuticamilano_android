package com.bioceuticamilano

// Shared data class for cart items used by CartActivity, CartFragment and CartAdapter
data class CartItem(
    val title: String,
    val badge: String,
    val price: String,
    val oldPrice: String,
    val thumbRes: Int,
    var qty: Int
)
