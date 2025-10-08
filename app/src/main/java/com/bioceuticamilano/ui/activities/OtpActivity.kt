package com.bioceuticamilano.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.bioceuticamilano.base.ActivityBase
import com.bioceuticamilano.databinding.ActivityOtpBinding
import com.bioceuticamilano.model.UserModel
import com.bioceuticamilano.network.ResponseHandler
import com.bioceuticamilano.network.RestCaller
import com.bioceuticamilano.network.RetrofitClient
import com.bioceuticamilano.ui.Constants
import com.bioceuticamilano.ui.fragments.ResendOtpBottomSheet
import com.bioceuticamilano.utils.Preferences
import com.bioceuticamilano.utils.Utility
import okhttp3.RequestBody
import org.json.JSONObject


class OtpActivity : ActivityBase(), ResponseHandler {

    private var isResendOTP= false
    private val loginRequestCode = 1
    private val verifyRequestCode = 2


    var email: String = ""
    var otp: String = ""


    private lateinit var binding: ActivityOtpBinding
    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var otp5: EditText
    private lateinit var otp6: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        email = intent.getStringExtra("email").toString()

        hitLoginApi()

        otp1 = binding.otp1
        otp2 = binding.otp2
        otp3 = binding.otp3
        otp4 = binding.otp4
        otp5 = binding.otp5
        otp6 = binding.otp6

        binding.btnBack.setOnClickListener { finish() }
        binding.resendOtp.setOnClickListener {
            isResendOTP = true
            hitResendOTPApi()
        }

        setupOtpAutoAdvance()

        // Handle continue button: validate entered 6-digit OTP and go to MainActivity
        binding.otpContinue.setOnClickListener {
            val code = listOf(
                binding.otp1.text?.toString().orEmpty(),
                binding.otp2.text?.toString().orEmpty(),
                binding.otp3.text?.toString().orEmpty(),
                binding.otp4.text?.toString().orEmpty(),
                binding.otp5.text?.toString().orEmpty(),
                binding.otp6.text?.toString().orEmpty()
            ).joinToString(separator = "")

            if (code.length != 6 || code.any { !it.isDigit() }) {
                Utility.showDialog(this, "Please enter a valid 6-digit code")
                return@setOnClickListener
            }else{
                hitVerifyOTPApi(code)
            }
        }
    }

    private fun setupOtpAutoAdvance() {
        val fields = listOf(otp1, otp2, otp3, otp4, otp5, otp6)
        fields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // enable continue button when all fields are filled
                    val allFilled = fields.all { it.text?.length == 1 }
                    binding.otpContinue.isEnabled = allFilled
                    binding.otpContinue.alpha = if (allFilled) 1f else 0.5f
                }
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (index < fields.size - 1) fields[index + 1].requestFocus()
                    } else if (s?.isEmpty() == true) {
                        if (index > 0) fields[index - 1].requestFocus()
                    }
                }
            })
        }
    }



    private fun hitLoginApi() {
        showWaitingDialog(activity)
        val params: MutableMap<String, RequestBody> = HashMap()
        params["email"] = Utility.getRequestParam(email)

        RestCaller(
            activity,
            this,
            RetrofitClient.getInstance().login(params),
            loginRequestCode
        )
    }
    private fun hitResendOTPApi() {
        showWaitingDialog(activity)
        val params: MutableMap<String, RequestBody> = HashMap()
        params["email"] = Utility.getRequestParam(email)

        RestCaller(
            activity,
            this,
            RetrofitClient.getInstance().resendOtp(params),
            loginRequestCode
        )
    }


    private fun hitVerifyOTPApi(code: String) {
        showWaitingDialog(activity)
        val params: MutableMap<String, RequestBody> = HashMap()
        params["email"] = Utility.getRequestParam(email)
        params["otp"] = Utility.getRequestParam(code)

        RestCaller(
            activity,
            this,
            RetrofitClient.getInstance().verifyOtp(params),
            verifyRequestCode
        )
    }

    override fun <T : Any?> onSuccess(response: Any, reqCode: Int) {
        if (reqCode == loginRequestCode) {
            dismissWaitingDialog(activity)
            try {
                val jsonObject = JSONObject(Utility.convertToString(response))
                Log.d("response", jsonObject.toString())

                if (!jsonObject.getBoolean("error")) {
                    val message = jsonObject.optString("message")
                     otp = jsonObject.optString("otp")

                    // You can show OTP or proceed to OTP verification screen here
                    Log.d("OTP", "OTP received: $otp")

                    if(isResendOTP.not()) {
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }else{
                        ResendOtpBottomSheet.Companion.show(this)
                    }

                } else {
                    handleErrorMessage(jsonObject, activity)
                }
            } catch (e: Exception) {
                Log.e("JSON_ERROR", e.message ?: "Unknown error")
                Utility.showDialog(activity, e.message, false)
            }
        }else if (reqCode == verifyRequestCode) {
            dismissWaitingDialog(activity)
            try {
                val jsonObject = JSONObject(Utility.convertToString(response))
                Log.d("response", jsonObject.toString())

                if (!jsonObject.getBoolean("error")) {
                    val message = jsonObject.optString("message")
                    val token = jsonObject.optString("token")
                    val dataObject = jsonObject.optJSONObject("data")

                    // Convert user data
                    val userModel: UserModel = Utility.convertObject(
                        dataObject?.toString(),
                        UserModel::class.java
                    ) as UserModel

                    // Save token
                    userModel.authToken = "Bearer $token"

                    // Save login session
                    Preferences.saveLoginDefaults(activity, userModel)

                    // Show message and navigate to main screen
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                    Utility.startActivity(
                        activity,
                        MainActivity::class.java,
                        Constants.CLEAR_BACK_STACK
                    )

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
