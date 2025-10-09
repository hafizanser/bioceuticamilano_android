package com.bioceuticamilano.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bioceuticamilano.R
import com.bioceuticamilano.base.ActivityBase
import com.bioceuticamilano.base.ActivityBase.Companion.activity
import com.bioceuticamilano.databinding.ActivityAddressEditBinding
import com.bioceuticamilano.model.Card
import com.bioceuticamilano.model.CardDetails
import com.bioceuticamilano.model.UserModel
import com.bioceuticamilano.network.ResponseHandler
import com.bioceuticamilano.network.RestCaller
import com.bioceuticamilano.network.RetrofitClient
import com.bioceuticamilano.responses.AddAddressResponse
import com.bioceuticamilano.utils.Preferences
import com.bioceuticamilano.utils.Utility
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.Gson
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.HashMap
import kotlin.collections.set

class AddressEditActivity :  ActivityBase(), ResponseHandler {

    private var _binding: ActivityAddressEditBinding? = null
    private val binding get() = _binding!!
    private val addResultRequestCode = 2
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

        // load existing if editing
        val id = intent.getIntExtra("id", -1)
        val name = intent.getStringExtra("name") ?: ""
        val fullAddress = intent.getStringExtra("fullAddress") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""

        // simple split for first/last name if provided in single name
        val parts = name.split(" ")
        if (parts.isNotEmpty()) binding.etFirstName.setText(parts.first())
        if (parts.size > 1) binding.etLastName.setText(parts.drop(1).joinToString(" "))

        binding.etMobile.setText(phone)
        binding.etFullAddress.setText(fullAddress)
        binding.tvAddress.text = fullAddress

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

        // ✅ Use String map (not RequestBody)
        val params: MutableMap<String, String> = HashMap()
        params["first_name"] = binding.etFirstName.text.toString().trim()
        params["last_name"] = binding.etLastName.text.toString().trim()
        params["mobile"] = binding.etMobile.text.toString().trim()
        // ✅ Get selected radio button text
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
        }
    }

    override fun onFailure(t: Throwable?, reqCode: Int) {
        onFailure(t.toString(), this)
    }

}
