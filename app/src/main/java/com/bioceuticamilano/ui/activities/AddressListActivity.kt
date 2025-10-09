package com.bioceuticamilano.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.bioceuticamilano.adapters.AddressAdapter
import com.bioceuticamilano.base.ActivityBase
import com.bioceuticamilano.databinding.ActivityAddressListBinding
import com.bioceuticamilano.network.ResponseHandler
import com.bioceuticamilano.network.RestCaller
import com.bioceuticamilano.network.RetrofitClient
import com.bioceuticamilano.responses.AddressListResponse
import com.bioceuticamilano.utils.Preferences
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class Address(
    @SerializedName("id") val id: Int?,
    @SerializedName("user_id") val userId: String,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("mobile") val mobile: String?,
    @SerializedName("full_address") val fullAddress: String?,
    @SerializedName("location_tag") val locationTag: String?,
    @SerializedName("is_default") val isDefault: Int?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)



class AddressListActivity : ActivityBase() , ResponseHandler {

    private var _binding: ActivityAddressListBinding? = null
    private val binding get() = _binding!!

    private val addresses = mutableListOf<Address>()
    private lateinit var adapter: AddressAdapter
    private var nextId = 1
    private val getAddressList = 1

    private val addEditLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult

            val id = data.getIntExtra("id", -1)
            val firstName = data.getStringExtra("firstName") ?: ""
            val lastName = data.getStringExtra("lastName") ?: ""
            val mobile = data.getStringExtra("mobile") ?: ""
            val fullAddress = data.getStringExtra("fullAddress") ?: ""
            val locationTag = data.getStringExtra("locationTag") ?: "Home"
            val isDefault = if (data.getBooleanExtra("isDefault", false)) 1 else 0

            if (id >= 0) {
                val idx = addresses.indexOfFirst { it.id == id }
                if (idx >= 0) {
                    addresses[idx] = Address(
                        id = id,
                        userId = "3", // or your logged-in user ID
                        firstName = firstName,
                        lastName = lastName,
                        mobile = mobile,
                        fullAddress = fullAddress,
                        locationTag = locationTag,
                        isDefault = isDefault,
                        createdAt = addresses[idx].createdAt,
                        updatedAt = SimpleDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
                            Locale.getDefault()
                        ).format(Date())
                    )
                    adapter.notifyItemChanged(idx)
                    return@registerForActivityResult
                }
            }

            val new = Address(
                id = nextId++,
                userId = "3", // or your logged-in user ID
                firstName = firstName,
                lastName = lastName,
                mobile = mobile,
                fullAddress = fullAddress,
                locationTag = locationTag,
                isDefault = isDefault,
                createdAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault()).format(Date()),
                updatedAt = null
            )
            addresses.add(0, new)
            adapter.notifyItemInserted(0)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // sample prefilled item (optional)
//        addresses.add(Address(nextId++, "HOME", "John Doe", "+966-99-1067004", "Al aidah madinah, almonoara, Madinah province 43314, Madinah, Saudia Arabia", true))


        hitGetAddressListApi()


        binding.rvAddresses.layoutManager = LinearLayoutManager(this)
        adapter = AddressAdapter(addresses,
            onEdit = { pos ->
                val addr = addresses[pos]
                val i = Intent(this, AddressEditActivity::class.java).apply {
                    putExtra("id", addr.id)
                    putExtra("fullAddress", addr.fullAddress)
                    putExtra("isDefault", addr.isDefault)
                }
                addEditLauncher.launch(i)
            }
        )
        binding.rvAddresses.adapter = adapter

        binding.btnAddNewAddress.setOnClickListener {
            val i = Intent(this, AddressEditActivity::class.java)
            addEditLauncher.launch(i)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
//

    private fun hitGetAddressListApi() {
        showWaitingDialog(activity)
        RestCaller(
            activity,
            this,
            RetrofitClient.getInstance()
                .getAddressList( Preferences.getUserDetails(activity).authToken),
            getAddressList
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun <T : Any?> onSuccess(response: Any?, reqCode: Int) {

       dismissWaitingDialog(this)
        if (reqCode == getAddressList) {
            try {
                val gson = Gson()
                val jsonObject = JSONObject(gson.toJson(response))
                val error = jsonObject.optBoolean("error", true)

                if (!error) {
                    val dataArray = jsonObject.optJSONArray("data")
                    if (dataArray != null && dataArray.length() > 0) {

                        // ✅ Correct TypeToken with Address model
                        val type = object : TypeToken<List<Address>>() {}.type
                        val addressList: List<Address> = gson.fromJson(dataArray.toString(), type)

                        // ✅ Optional: Move default address (is_default == 1) to top
                        val sortedList = addressList.sortedByDescending { it.isDefault ?: 0 }

                        // ✅ Debug log for verification
                        for (address in sortedList) {
                            Log.d(
                                "AddressList",
                                "ID: ${address.id}, Tag: ${address.locationTag}, Default: ${address.isDefault}"
                            )
                        }

                        // ✅ Update your RecyclerView adapter
                        adapter.setData(sortedList)

                        dismissWaitingDialog(this)
                    } else {
                        Log.d("AddressList", "No addresses found")
                    }
                } else {
                    Log.e("AddressList", "Error in response: ${jsonObject.optString("message")}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("AddressList", "Exception while parsing response: ${e.message}")
            }
        }
    }



    override fun onFailure(t: Throwable?, reqCode: Int) {
        onFailure(t!!.message.toString(), activity)
    }
}
