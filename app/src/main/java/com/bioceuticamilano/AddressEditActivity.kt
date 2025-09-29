package com.bioceuticamilano

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bioceuticamilano.databinding.ActivityAddressEditBinding

class AddressEditActivity : AppCompatActivity() {

    private var _binding: ActivityAddressEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddressEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
