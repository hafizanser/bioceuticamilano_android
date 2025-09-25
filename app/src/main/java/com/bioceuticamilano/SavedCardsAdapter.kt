package com.bioceuticamilano


import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bioceuticamilano.databinding.ListItemCardsBinding
import com.bioceuticamilano.model.Card

class SavedCardsAdapter(
    context: Activity,
    var callBack: ((it: String, card: Card?) -> Unit)
) :
    RecyclerView.Adapter<SavedCardsAdapter.ItemViewHolder>() {
    var contexts = context
    lateinit var binding: ListItemCardsBinding
    var mList: ArrayList<Card> = ArrayList()
    var selected: Card = Card()

    fun addData(mList: ArrayList<Card>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ListItemCardsBinding.inflate(inflater, parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.mList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val model = mList[position]

        holder.binding.tvNumber.text = "**** **** **** " + model.cardDetails?.last4
        holder.binding.tvExpiry.text = "Expires on " + model.cardDetails?.expMonth + "/" + model.cardDetails?.expYear
        holder.binding.tvName.text = model.cardDetails?.name

//        holder.binding.rootView.setOnClickListener {
//            selected=model
//            callBack("booking", model)
//        }

        holder.binding.ivDelete.setOnClickListener {
            selected = model
            callBack("delete", model)
        }

        // Show/hide set default and default label
        if (model.isDefault == true) {
        } else {
            holder.binding.rootView.setOnClickListener {
                selected = model
                callBack("setDefault", model)
//                CarDetailActivity.selectedCard = selected

//                val userModel = Preferences.getUserDetails(activity)
//                if (userModel != null) {
//                    val defaultCard = UserModel.DefaultCard()
//                    defaultCard.id = selected.id
//                    defaultCard.last4 = selected.cardDetails?.last4 ?: ""
//                    defaultCard.name = selected.cardDetails?.name ?: ""
//                    defaultCard.expMonth = selected.cardDetails?.expMonth?.toString() ?: ""
//                    defaultCard.expYear = selected.cardDetails?.expYear?.toString() ?: ""
//                    defaultCard.isDefault = true
//                    userModel.defaultCard = defaultCard
//                    Preferences.saveLoginDefaults(activity, userModel)
//                }
            }
        }
    }

    class ItemViewHolder(var binding: ListItemCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

}