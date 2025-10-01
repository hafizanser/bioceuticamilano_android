package com.bioceuticamilano

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(private val items: List<WishlistItem>) : RecyclerView.Adapter<WishlistAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val ivThumb: ImageView = view.findViewById(R.id.ivThumb)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvOldPrice: TextView = view.findViewById(R.id.tvOldPrice)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val btnAddToCart: View = view.findViewById(R.id.tvAddToCart)
        val ivFav: ImageView = view.findViewById(R.id.ivFav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.ivThumb.setImageResource(item.thumbRes)
        holder.tvTitle.text = item.title
        holder.tvOldPrice.text = item.oldPrice
        holder.tvPrice.text = item.price

        holder.btnAddToCart.setOnClickListener {
            // placeholder: add to cart
        }
        holder.ivFav.setOnClickListener {
            // placeholder: toggle favorite
        }
    }

    override fun getItemCount(): Int = items.size
}
