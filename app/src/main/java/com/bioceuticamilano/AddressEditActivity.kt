package com.bioceuticamilano

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bioceuticamilano.databinding.ActivityAddressEditBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class AddressEditActivity : AppCompatActivity() {

    private var _binding: ActivityAddressEditBinding? = null
    private val binding get() = _binding!!

    private val autocompleteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult
            val place = Autocomplete.getPlaceFromIntent(data)
            val address = place.address ?: place.name ?: ""
            binding.tvAddress.text = address
            binding.etFullAddress.setText(address)
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
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
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        binding.btnSave.setOnClickListener {
            val out = intent
            out.putExtra("id", id)
            out.putExtra("label", "HOME")
            out.putExtra("name", (binding.etFirstName.text.toString() + " " + binding.etLastName.text.toString()).trim())
            out.putExtra("phone", binding.etMobile.text.toString())
            out.putExtra("fullAddress", binding.etFullAddress.text.toString())
            out.putExtra("isDefault", binding.cbDefault.isChecked)
            setResult(Activity.RESULT_OK, out)
            finish()
        }
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
}
