package com.bioceuticamilano.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bioceuticamilano.base.ActivityBase
import com.bioceuticamilano.databinding.ActivityEditProfileBinding
import com.bioceuticamilano.network.ResponseHandler
import com.bioceuticamilano.network.RestCaller
import com.bioceuticamilano.network.RetrofitClient
import com.bioceuticamilano.ui.fragments.ResendOtpBottomSheet
import com.bioceuticamilano.utils.Preferences
import com.bioceuticamilano.utils.Utility
import okhttp3.RequestBody
import org.json.JSONObject

class EditProfileActivity : ActivityBase() ,ResponseHandler{
    private lateinit var binding: ActivityEditProfileBinding
    private val editProfileRequestCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        val userModel= Preferences.getUserDetails(this)
        // prefill
        binding.etFullName.setText(userModel.fullName)
        binding.etEmail.setText(userModel.email)

        binding.btnSave.setOnClickListener {
            hitEditApi()

        }
    }

    private fun hitEditApi() {
        showWaitingDialog(activity)
        val params: MutableMap<String, RequestBody> = HashMap()
        params["full_name"] = Utility.getRequestParam(binding.etFullName.text.toString())

        RestCaller(
            activity,
            this,
            RetrofitClient.getInstance().updateProfile(Preferences.getUserDetails(this).authToken,params),
            editProfileRequestCode
        )
    }

    override fun <T : Any?> onSuccess(response: Any?, reqCode: Int) {
        if (reqCode == editProfileRequestCode) {
            dismissWaitingDialog(activity)
            try {
                val jsonObject = JSONObject(Utility.convertToString(response))
                Log.d("response", jsonObject.toString())

                if (!jsonObject.getBoolean("error")) {


                    val userModel = Preferences.getUserDetails(this)
                    userModel.fullName = binding.etFullName.text.toString()

                    Preferences.saveLoginDefaults(activity, userModel)
                    val message = jsonObject.optString("message")
                    Toast.makeText(this,message, Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    handleErrorMessage(jsonObject, activity)
                }
            } catch (e: Exception) {
                Log.e("JSON_ERROR", e.message ?: "Unknown error")
                Utility.showDialog(activity, e.message, false)
            }
        }
    }

    override fun onFailure(t: Throwable?, reqCode: Int) {
        onFailure(activity, t!!.message.toString())
    }

}
