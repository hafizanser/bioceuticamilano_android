package com.bioceuticamilano.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bioceuticamilano.R

// simple product model used by adapter
data class Product(val title: String, val price: String, val imageRes: Int)

class ProductAdapter(private val items: List<Product>) : RecyclerView.Adapter<ProductAdapter.VH>() {
    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val ivProduct: ImageView = view.findViewById(R.id.ivProduct)
        val tvTitle: TextView = view.findViewById(R.id.tvProductTitle)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product_card, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvPrice.text = item.price
        holder.ivProduct.setImageResource(item.imageRes)
    }

    override fun getItemCount(): Int = items.size
}
