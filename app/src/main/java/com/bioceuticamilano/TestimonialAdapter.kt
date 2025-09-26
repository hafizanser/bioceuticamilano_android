package com.bioceuticamilano

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.floor

class TestimonialAdapter(private val items: List<Testimonial>) : RecyclerView.Adapter<TestimonialAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val star1: ImageView = view.findViewById(R.id.star1)
        val star2: ImageView = view.findViewById(R.id.star2)
        val star3: ImageView = view.findViewById(R.id.star3)
        val star4: ImageView = view.findViewById(R.id.star4)
        val star5: ImageView = view.findViewById(R.id.star5)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
        val tvTestimonial: TextView = view.findViewById(R.id.tvTestimonial)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_testimonial, parent, false)

        // ensure the item fills the RecyclerView width (page-like)
        val displayWidth = if (parent.measuredWidth > 0) parent.measuredWidth
        else parent.context.resources.displayMetrics.widthPixels
        view.layoutParams = RecyclerView.LayoutParams(displayWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val t = items[position]
        holder.ivAvatar.setImageResource(t.avatarRes)
        holder.tvName.text = t.name
        holder.tvTestimonial.text = t.comment
        holder.tvRating.text = String.format(java.util.Locale.getDefault(), "%.1f", t.rating)

        val fullTint = ContextCompat.getColor(holder.itemView.context, R.color.star_yellow)
        val emptyTint = ContextCompat.getColor(holder.itemView.context, R.color.light_gray_color)

        val stars = listOf(holder.star1, holder.star2, holder.star3, holder.star4, holder.star5)
        val fullCount = floor(t.rating).toInt()
        for (i in stars.indices) {
            val star = stars[i]
            if (i < fullCount) {
                star.setColorFilter(fullTint, PorterDuff.Mode.SRC_IN)
            } else {
                star.setColorFilter(emptyTint, PorterDuff.Mode.SRC_IN)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
