package com.bioceuticamilano

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryAdapter(private val items: List<OrderHistoryItem>) : RecyclerView.Adapter<OrderHistoryAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvOrderNum: TextView = view.findViewById(R.id.tvOrderNum)
        val tvArticles: TextView = view.findViewById(R.id.tvArticles)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvShippingTitle: TextView = view.findViewById(R.id.tvShippingTitle)
        val tvShippingStatus: TextView = view.findViewById(R.id.tvShippingStatus)
        val thumbnailsContainer: ViewGroup = view.findViewById(R.id.thumbnailsContainer)
        val btnBuyAgain: View = view.findViewById(R.id.btnBuyAgain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_order_history, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvOrderNum.text = item.orderNumber
        holder.tvArticles.text = "${item.articlesCount} Articles"
        holder.tvPrice.text = item.price
        holder.tvShippingStatus.text = item.shippingStatus

        // thumbnails
        holder.thumbnailsContainer.removeAllViews()
        val ctx = holder.itemView.context
        item.thumbnails.forEach { resId ->
            val iv = ImageView(ctx)
            val lp = ViewGroup.MarginLayoutParams(
                ctx.resources.getDimensionPixelSize(R.dimen._56sdp),
                ctx.resources.getDimensionPixelSize(R.dimen._56sdp)
            )
            lp.marginEnd = ctx.resources.getDimensionPixelSize(R.dimen._8sdp)
            iv.layoutParams = lp
            iv.setImageResource(resId)
            iv.scaleType = ImageView.ScaleType.CENTER_CROP
            iv.clipToOutline = true
            holder.thumbnailsContainer.addView(iv)
        }

        holder.btnBuyAgain.setOnClickListener {
            // placeholder: implement buy-again action
        }
    }

    override fun getItemCount(): Int = items.size
}