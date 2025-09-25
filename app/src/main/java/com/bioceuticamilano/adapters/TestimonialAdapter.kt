package com.bioceuticamilano.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bioceuticamilano.R

data class Testimonial(val name: String, val rating: String, val message: String, val avatarRes: Int)

class TestimonialAdapter(private val items: List<Testimonial>) : RecyclerView.Adapter<TestimonialAdapter.VH>() {
    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
        val tvTestimonial: TextView = view.findViewById(R.id.tvTestimonial)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_testimonial, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvRating.text = item.rating
        holder.tvTestimonial.text = item.message
        holder.ivAvatar.setImageResource(item.avatarRes)
    }

    override fun getItemCount(): Int = items.size
}
