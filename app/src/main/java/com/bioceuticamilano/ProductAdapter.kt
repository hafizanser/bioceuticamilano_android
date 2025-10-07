package com.bioceuticamilano

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val items: List<ProductItem>) : RecyclerView.Adapter<ProductAdapter.VH>() {
    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.findViewById(R.id.ivProduct)
        val title: TextView = v.findViewById(R.id.tvProductTitle)
        val price: TextView = v.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product_card, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        holder.title.text = p.title
        holder.price.text = p.price
        holder.image.setImageResource(p.image)
    }

    override fun getItemCount(): Int = items.size
}
