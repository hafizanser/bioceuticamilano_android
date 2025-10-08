package com.bioceuticamilano.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bioceuticamilano.R
import com.bioceuticamilano.ui.activities.Address

class AddressAdapter(
    private val items: MutableList<Address>,
    private val onEdit: (Int) -> Unit
) : RecyclerView.Adapter<AddressAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLabel: TextView = itemView.findViewById(R.id.tvLabel)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val btnEdit: ImageView = itemView.findViewById(R.id.ivEditIcon)
        val tvDefaultBadge: TextView = itemView.findViewById(R.id.tvDefaultBadge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val a = items[position]
        holder.tvLabel.text = a.locationTag ?: ""
        holder.tvName.text = "${a.firstName ?: ""} ${a.lastName ?: ""}".trim()
        holder.tvAddress.text = a.fullAddress ?: ""
        holder.tvPhone.text = a.mobile ?: ""

        if(a.isDefault==1) {
            holder.tvDefaultBadge.visibility = View.VISIBLE
        } else{
            holder.tvDefaultBadge.visibility = View.GONE
        }
        holder.btnEdit.setOnClickListener { onEdit(position) }
    }

    override fun getItemCount(): Int = items.size

    /** ✅ Add this method to update the data list **/
    fun setData(newList: List<Address>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}
