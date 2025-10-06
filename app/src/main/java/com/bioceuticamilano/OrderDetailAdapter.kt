package com.bioceuticamilano

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderDetailAdapter(private val items: List<OrderDetailItem>) : RecyclerView.Adapter<OrderDetailAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val ivThumb: ImageView? = view.findViewById(R.id.ivThumb)
        val tvTitle: TextView? = view.findViewById(R.id.tvTitle)
        // some layouts use tvQty, others use tvQtyBadge
        val tvQty: TextView? = view.findViewById(R.id.tvQty) ?: view.findViewById(R.id.tvQtyBadge)
        val tvPrice: TextView? = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.ivThumb?.setImageResource(item.thumbRes)
        holder.tvTitle?.text = item.title
        holder.tvQty?.text = "${item.qty}"
        holder.tvPrice?.text = item.price
    }

    override fun getItemCount(): Int = items.size
}
