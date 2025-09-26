package com.bioceuticamilano

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bioceuticamilano.base.ActivityBase
import com.bioceuticamilano.base.ActivityBase.Companion.activity
import com.bioceuticamilano.databinding.ActivitySavedCardsBinding
import com.bioceuticamilano.model.Card
import com.bioceuticamilano.network.ResponseHandler
import com.bioceuticamilano.network.RestCaller
import com.bioceuticamilano.network.RetrofitClient
import com.bioceuticamilano.utils.Preferences
import com.bioceuticamilano.utils.Preferences.deleteDefaultCard
import com.bioceuticamilano.utils.Utility
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class SavedCardsActivity : ActivityBase(), ResponseHandler {

    lateinit var binding: ActivitySavedCardsBinding
    private var getData = 888
    private var deleteApi = 898
    private var setDefaultApi = 899
    lateinit var adapter: SavedCardsAdapter

    override fun onResume() {
        super.onResume()
        activity = this
        getList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setAdapter()
    }

    private fun getList() {
        showWaitingDialog(activity)
        // Safely retrieve user's auth token; fall back to default token if user details are missing
        val userModel = try { Preferences.getUserDetails(activity) } catch (e: Exception) { null }
        val authToken = userModel?.authToken ?: "7437|R844uSJ6lg2jcnYRGcNFlvJ21Fg3PiXaupwOdFlU"

        RestCaller(
            activity,
            this,
            RetrofitClient.getInstance()
                .getAllCreditCards(authToken),
            getData
        )
    }

    private fun setAdapter() {
        binding.rvOtherCards.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = SavedCardsAdapter(activity) { action, card ->
            when (action) {
                "booking" -> {
//                    CarDetailActivity.selectedCard = adapter.selected
                    onBackPressed()
                }
                "delete" -> {
                    deleteCard()
                }
                "setDefault" -> {
                    card?.let { setDefaultCard(it.id.toString()) }
                }
            }
        }
        binding.rvOtherCards.adapter = adapter
    }

    private fun deleteCard() {
        showWaitingDialog(activity)
        RestCaller(
            activity,
            this,
            RetrofitClient.getInstance()
                .deleteCards(
                    Preferences.getUserDetails(activity).authToken,
                    adapter.selected.id.toString()
                ),
            deleteApi
        )
    }

    private fun setDefaultCard(cardId: String) {
        showWaitingDialog(activity)
        RestCaller(
            activity,
            this,
            RetrofitClient.getInstance()
                .makeDefault(Preferences.getUserDetails(activity).authToken, cardId),
            setDefaultApi
        )
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
//            CarDetailActivity.selectedCard = null
            onBackPressed()
        }
        binding.btnAddCard.setOnClickListener {
            Utility.startActivity(activity, AddCardActivity::class.java, Constants.START_ACTIVITY)
        }
    }

    override fun <T : Any?> onSuccess(response: Any, reqCode: Int) {
        if (reqCode == getData) {
            dismissWaitingDialog(activity)
            try {
                val `object` = JSONObject(Utility.convertToString(response))
                if (!`object`.getBoolean("error")) {

                    val jsonArray = `object`.getJSONArray(Constants.KEY_DATA)
                    val gson = Gson()
                    val type = object : TypeToken<List<Card>>() {}.type

                    val cardList: ArrayList<Card> = gson.fromJson(jsonArray.toString(), type)

                    // Find default card and others
                    val defaultCard = cardList.find { it.isDefault == true }
                    val otherCards = cardList.filter { it.isDefault != true }

                    // If there is no default but there are other cards, make the first one default
                    if (defaultCard == null && otherCards.isNotEmpty()) {
                        // set first available card as default
                        setDefaultCard(otherCards[0].id.toString())
                        // wait for setDefault API to complete and refresh list
                        return
                    }

                    // Log the default card for debugging
                    Log.d("DefaultCard", defaultCard?.toString() ?: "null")

                    // Show/hide default card section
                    if (defaultCard != null) {
                        binding.layoutCardView.visibility = View.VISIBLE
                        binding.noCardLayout.visibility = View.GONE
                        // Bind default card data to the include layout
                        val defaultCardBinding = com.bioceuticamilano.databinding.DefaultItemCardsBinding.bind(binding.layoutDefaultCard.rootView)
                        defaultCardBinding.tvNumber.text = "**** **** **** " + (defaultCard.cardDetails?.last4 ?: "")
                        defaultCardBinding.tvExpiry.text = "Expires on " + (defaultCard.cardDetails?.expMonth ?: "") + "/" + (defaultCard.cardDetails?.expYear ?: "")
                        defaultCardBinding.tvName.text = defaultCard.cardDetails?.name ?: ""
                        defaultCardBinding.ivDelete.setOnClickListener {
                            adapter.selected = defaultCard
                            deleteCard()
                        }
                        Preferences.saveDefaultCard(activity, defaultCard)
                    } else {
                        binding.layoutCardView.visibility = View.GONE
                        deleteDefaultCard(this)

                        // Also clear default card from the saved user details so callers (e.g. CarDetailActivity)
                        // see there is no default card and prompt to add a new payment method.
                        try {
                            val userModel = Preferences.getUserDetails(activity)
                            if (userModel != null) {
                                userModel.defaultCard = null
                                Preferences.saveLoginDefaults(activity, userModel)
                            }
                        } catch (e: Exception) {
                            Log.e("SavedCardsActivity", "Error clearing default card in preferences: ${e.message}")
                        }
                    }

                    // Update adapter with non-default cards
                    adapter.addData(ArrayList(otherCards))

                    // If only one card and not default, set as default
                    if (cardList.size == 1 && cardList[0].isDefault != true) {
                        setDefaultCard(cardList[0].id.toString())
                    }

                    // Fix setViews call at line 190
                    setViews(
                        hasDefaultCard = (binding.layoutCardView.visibility == View.VISIBLE),
                        hasOtherCards = adapter.mList.isNotEmpty()
                    )


                } else {
                    handleErrorMessage(`object`, activity)
                }
            } catch (e: Exception) {
                Log.e("JSON_ERROR", "" + e.message)
                Utility.showDialog(activity, e.message, false)
            }
        } else if (reqCode == deleteApi) {
            dismissWaitingDialog(activity)
            try {
                val `object` = JSONObject(Utility.convertToString(response))
                if (!`object`.getBoolean("error")) {
                    getList()
                } else {
                    handleErrorMessage(`object`, activity)
                }
            } catch (e: Exception) {
                Log.e("JSON_ERROR", "" + e.message)
                Utility.showDialog(activity, e.message, false)
            }
        } else if (reqCode == setDefaultApi) {
            dismissWaitingDialog(activity)

            getList()
        }

    }


    private fun setViews(hasDefaultCard: Boolean, hasOtherCards: Boolean) {
        if (!hasDefaultCard && !hasOtherCards) {
            binding.layoutCardView.visibility = View.GONE
            binding.tvSetDefault.visibility = View.GONE
            binding.noCardLayout.visibility = View.VISIBLE
        } else {
            binding.noCardLayout.visibility = View.GONE
            binding.tvOtherCards.visibility = View.VISIBLE
            binding.tvSetDefault.visibility = View.VISIBLE
            binding.layoutCardView.visibility = View.VISIBLE
        // Only show layoutCardView if there is a default card (already handled in onSuccess)
        }
        if(hasOtherCards.not()) {
            binding.tvOtherCards.visibility = View.GONE
            binding.tvSetDefault.visibility = View.GONE
        }
    }

    override fun onFailure(t: Throwable?, reqCode: Int) {
        onFailure(t!!.message.toString(), activity)
    }
}