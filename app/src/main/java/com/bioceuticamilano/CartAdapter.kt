package com.bioceuticamilano

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val items: MutableList<CartItem>) : RecyclerView.Adapter<CartAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val ivThumb: ImageView = view.findViewById(R.id.ivThumb)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvBadge: TextView = view.findViewById(R.id.tvBadge)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvOldPrice: TextView = view.findViewById(R.id.tvOldPrice)
        val btnMinus: View = view.findViewById(R.id.btnMinus)
        val btnPlus: View = view.findViewById(R.id.btnPlus)
        val tvQty: TextView = view.findViewById(R.id.tvQty)
        val ivDelete: ImageView = view.findViewById(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.ivThumb.setImageResource(item.thumbRes)
        holder.tvTitle.text = item.title
        holder.tvBadge.text = item.badge
        holder.tvPrice.text = item.price
        holder.tvOldPrice.text = item.oldPrice
        holder.tvQty.text = item.qty.toString()

        holder.btnMinus.setOnClickListener {
            if (item.qty > 1) {
                item.qty--
                holder.tvQty.text = item.qty.toString()
            }
        }
        holder.btnPlus.setOnClickListener {
            item.qty++
            holder.tvQty.text = item.qty.toString()
        }
        holder.ivDelete.setOnClickListener {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = items.size
}
