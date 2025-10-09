package com.bioceuticamilano.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bioceuticamilano.R
import com.bioceuticamilano.base.ActivityBase
import com.bioceuticamilano.databinding.ActivityAddressEditBinding
import com.bioceuticamilano.network.ResponseHandler
import com.bioceuticamilano.network.RestCaller
import com.bioceuticamilano.network.RetrofitClient
import com.bioceuticamilano.responses.AddAddressResponse
import com.bioceuticamilano.responses.GetAddressResponse
import com.bioceuticamilano.utils.Preferences
import com.bioceuticamilano.utils.Utility
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.Gson
import java.util.HashMap
import kotlin.collections.set

class AddressEditActivity :  ActivityBase(), ResponseHandler {

    private var _binding: ActivityAddressEditBinding? = null
    private val binding get() = _binding!!
    private val addResultRequestCode = 2
    private val getAddressRequestCode = 1
    private val autocompleteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult
            val place = Autocomplete.getPlaceFromIntent(data)
            val address = place.address ?: place.name ?: ""
            binding.tvAddress.text = address
            binding.etFullAddress.setText(address)
        } else if (result.resultCode == RESULT_CANCELED) {
            // user cancelled
        } else {
            val data = result.data
            try {
                val status = Autocomplete.getStatusFromIntent(data)
                Log.w("AddressEdit", "Autocomplete error: ${status.statusMessage}")
            } catch (e: Exception) {
                Log.w("AddressEdit", "Autocomplete unknown result", e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddressEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Places if needed (uses same API key as maps)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_maps_key))
        }

        // Country code picker setup
        binding.ccp.setOnCountryChangeListener {
            // Update flag and code when country changes
            binding.ivFlag.setImageDrawable(binding.ccp.imageViewFlag.drawable)
            binding.tvCc.text = "+" + binding.ccp.selectedCountryCode
        }
        // Set initial flag and code
        binding.ivFlag.setImageDrawable(binding.ccp.imageViewFlag.drawable)
        binding.tvCc.text = "+" + binding.ccp.selectedCountryCode

        // Open picker when flag, arrow, or code is clicked
        val openPicker = View.OnClickListener { binding.ccp.launchCountrySelectionDialog() }
        binding.ivFlag.setOnClickListener(openPicker)
        binding.ivArrow.setOnClickListener(openPicker)
        binding.tvCc.setOnClickListener(openPicker)


        getDataForEdit()


        binding.tvAddress.setOnClickListener {
            openLocationSearch()
        }

        binding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        binding.btnSave.setOnClickListener {

            if (isValidated()) {
                addAddressApi()
            }
        }
    }

    private fun getDataForEdit() {
        val id = intent.getIntExtra("id", -1)
        loadAddresses(id)
    }


    private fun loadAddresses(id: Int) {
        showWaitingDialog(this)

        // Example ID — you can replace this with the user's or selected address ID

        RestCaller(
            this,
            this,
            RetrofitClient.getInstance().getAddressDetail(
                Preferences.getUserDetails(this).authToken,
                id
            ),
            getAddressRequestCode
        )
    }



    private fun isValidated(): Boolean {
        when {
            binding.etFirstName.text.toString().trim().isEmpty() -> {
                binding.etFirstName.error = "Please enter first name"
                binding.etFirstName.requestFocus()
                return false
            }
            binding.etLastName.text.toString().trim().isEmpty() -> {
                binding.etLastName.error = "Please enter last name"
                binding.etLastName.requestFocus()
                return false
            }
            binding.etMobile.text.toString().trim().isEmpty() -> {
                binding.etMobile.error = "Please enter mobile number"
                binding.etMobile.requestFocus()
                return false
            }
            binding.etMobile.text.toString().length < 8 -> {
                binding.etMobile.error = "Invalid mobile number"
                binding.etMobile.requestFocus()
                return false
            }
            binding.etFullAddress.text.toString().trim().isEmpty() -> {
                binding.etFullAddress.error = "Please enter address tag"
                binding.etFullAddress.requestFocus()
                return false
            }
            binding.tvAddress.text.toString().trim().isEmpty() -> {
                Toast.makeText(this, "Please select full address", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return true
        }
    }



    private fun addAddressApi() {
        showWaitingDialog(this)

        val params: MutableMap<String, String> = HashMap()
        params["first_name"] = binding.etFirstName.text.toString().trim()
        params["last_name"] = binding.etLastName.text.toString().trim()
        params["mobile"] = binding.etMobile.text.toString().trim()
        val selectedRadioButtonId = binding.rgLocationTag.checkedRadioButtonId
        val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
        params["location_tag"] = selectedRadioButton.text.toString().trim()
        params["is_default"] = if (binding.cbDefault.isChecked) "1" else "0"
        params["full_address"] = binding.tvAddress.text.toString().trim()

        // ✅ Log all values before sending
        Log.d("API_PARAMS", "========== ADD ADDRESS REQUEST ==========")
        for ((key, value) in params) {
            Log.d("API_PARAMS", "$key : $value")
        }
        Log.d("API_PARAMS", "========================================")

        // ✅ Call API
        RestCaller(
            this,
            this,
            RetrofitClient.getInstance().addAddress(
                Preferences.getUserDetails(this).authToken,
                params
            ),
            addResultRequestCode
        )
    }



    private fun openLocationSearch() {
        try {
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this)
            autocompleteLauncher.launch(intent)
        } catch (e: Exception) {
            Log.e("AddressEdit", "Error opening location search: ${e.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun <T : Any?> onSuccess(response: Any?, reqCode: Int) {
        dismissWaitingDialog(this)

        if (reqCode == addResultRequestCode) {
            try {
                val addResponse = response as AddAddressResponse
                if (addResponse.error == false) {
                    Utility.showSuccessDialog(activity, addResponse.message ?: "Address Added", true)

                    addResponse.data?.let { data ->
                        val out = Intent().apply {
                            putExtra("id", data.id ?: -1)
                            putExtra("label", data.locationTag ?: "Home")
                            putExtra("name", "${data.firstName} ${data.lastName}".trim())
                            putExtra("phone", data.mobile ?: "")
                            putExtra("fullAddress", data.fullAddress ?: "")
                            putExtra("isDefault", data.isDefault == "1")
                        }
                        setResult(RESULT_OK, out)
                        finish()

                        Log.d("API_RESPONSE", Gson().toJson(addResponse))
                    }
                } else {
                    Utility.showDialog(activity, addResponse.message ?: "Error", false)
                }
            } catch (e: Exception) {
                Log.e("PARSING_ERROR", "Response was not JSON: ${e.message}")
                Utility.showDialog(activity, "Unexpected response from server", false)

                Log.e("JSON_ERROR", e.message ?: "Unknown error")
                Utility.showDialog(activity, "Something went wrong. Please try again.", false)
            }
        }else if (reqCode == getAddressRequestCode) {
            try {
                val addressResponse = response as GetAddressResponse
                if (addressResponse.error == false) {
                    addressResponse.data?.let { data ->

                        // ✅ Fill EditText fields
                        binding.etFirstName.setText(data.firstName ?: "")
                        binding.etLastName.setText(data.lastName ?: "")
                        binding.etMobile.setText(data.mobile ?: "")
                        binding.tvAddress.text = data.fullAddress ?: ""

                        // ✅ Select the correct RadioButton for location_tag
                        when (data.locationTag?.lowercase()) {
                            "home" -> binding.rbHome.isChecked = true
                            "office" -> binding.rbOffice.isChecked = true
                            else -> binding.rbHome.isChecked = true // default
                        }

                        // ✅ Set checkbox for default address
                        binding.cbDefault.isChecked = data.isDefault == 1

                        // ✅ Log everything for verification
                        Log.d("ADDRESS_DATA", "First Name   : ${data.firstName}")
                        Log.d("ADDRESS_DATA", "Last Name    : ${data.lastName}")
                        Log.d("ADDRESS_DATA", "Mobile       : ${data.mobile}")
                        Log.d("ADDRESS_DATA", "Full Address : ${data.fullAddress}")
                        Log.d("ADDRESS_DATA", "Location Tag : ${data.locationTag}")
                        Log.d("ADDRESS_DATA", "Is Default   : ${data.isDefault}")
                    }
                } else {
                    Utility.showDialog(this, addressResponse.message ?: "Failed to load address", false)
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", e.message ?: "Unknown error")
                Utility.showDialog(this, "Something went wrong. Please try again.", false)
            }
        }

    }

    override fun onFailure(t: Throwable?, reqCode: Int) {
        onFailure(t.toString(), this)
    }

}
