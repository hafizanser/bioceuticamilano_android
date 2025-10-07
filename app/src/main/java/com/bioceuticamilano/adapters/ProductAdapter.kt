package com.bioceuticamilano.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bioceuticamilano.R

// simple product model used by adapter
data class Product(val title: String, val price: String, val originalPrice: String, val imageRes: Int)

class ProductAdapter(private val items: List<Product>, private val showFav: Boolean = false) : RecyclerView.Adapter<ProductAdapter.VH>() {
    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val ivProduct: ImageView = view.findViewById(R.id.ivProduct)
        val tvTitle: TextView = view.findViewById(R.id.tvProductTitle)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvOriginalPrice: TextView = view.findViewById(R.id.tvOriginalPrice)
        // favorite icon on product card
        val ivFav: ImageView? = view.findViewById(R.id.ivFav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product_card, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvPrice.text = item.price
        holder.tvOriginalPrice.text = item.originalPrice
        // apply strikethrough to original price
        holder.tvOriginalPrice.paintFlags = holder.tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.ivProduct.setImageResource(item.imageRes)

        // show or hide favourite icon depending on flag
        holder.ivFav?.visibility = if (showFav) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int = items.size
}
