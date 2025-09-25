package com.bioceuticamilano

import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import okhttp3.RequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import android.text.Editable
import android.text.TextWatcher
import android.text.InputFilter
import com.bioceuticamilano.base.ActivityBase
import com.bioceuticamilano.databinding.ActivityAddCardBinding
import com.bioceuticamilano.model.Card
import com.bioceuticamilano.model.CardDetails
import com.bioceuticamilano.model.UserModel
import com.bioceuticamilano.network.ResponseHandler
import com.bioceuticamilano.network.RestCaller
import com.bioceuticamilano.network.RetrofitClient
import com.bioceuticamilano.utils.CustomDatePickerDialog
import com.bioceuticamilano.utils.Preferences
import com.bioceuticamilano.utils.Utility

class AddCardActivity : ActivityBase(), ResponseHandler {

    lateinit var binding: ActivityAddCardBinding
    private val myCalendar: Calendar = Calendar.getInstance()
    lateinit var date: OnDateSetListener
    private val editCardRequestCode = 1
    private val addCardRequestCode = 2
    var addCard: Boolean = false
    var isEditMode: Boolean = false
    var editingCard: Card? = null
    lateinit var token: String

    override fun onResume() {
        super.onResume()
        activity = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if we're in edit mode
        if (intent.hasExtra("isEditMode") && intent.getBooleanExtra("isEditMode", false)) {
            isEditMode = true
//            editingCard = CarDetailActivity.selectedCard
            setupEditMode()
        }

        setListeners()

        date = OnDateSetListener { _, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateLabel()
        }

        // Format card number input with spaces every 4 digits
        setupCardNumberFormatting()
    }

    private fun setListeners() {
        binding.etExpiry.setOnClickListener {
            val datePickerDialog = CustomDatePickerDialog(
                activity,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            
            datePickerDialog.show()
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSubmit.setOnClickListener {
            if (checkValidation()) {
                addCardApi()
            }
        }
    }

    private fun setupCardNumberFormatting() {
        // Limit to 19 characters (16 digits + 3 spaces)
        binding.etCardNumber.filters = arrayOf(InputFilter.LengthFilter(19))

        var isFormatting = false
        binding.etCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                val digits = s.toString().replace("\\s".toRegex(), "").take(16)
                val grouped = StringBuilder()
                for ((index, ch) in digits.withIndex()) {
                    if (index > 0 && index % 4 == 0) grouped.append(' ')
                    grouped.append(ch)
                }

                // Only update if formatting changed
                val formatted = grouped.toString()
                if (formatted != s.toString()) {
                    val cursor = binding.etCardNumber.selectionStart
                    binding.etCardNumber.setText(formatted)
                    // Reposition cursor near end safely
                    val newPos = (cursor + (formatted.length - (s?.length ?: 0))).coerceAtMost(
                        formatted.length
                    )
                    binding.etCardNumber.setSelection(newPos.coerceAtLeast(0))
                }

                isFormatting = false
            }
        })
    }

    private fun checkValidation(): Boolean {
        val cardDigits = binding.etCardNumber.text.toString().replace("\\D".toRegex(), "")
        return if (!isEditMode && cardDigits.length != 16) {
            Utility.showDialog(activity, "Invalid Card Number")
            false
        } else if (binding.etFirstName.text.toString().trim().length < 4) {
            Utility.showDialog(activity, "First name must be at least 4 characters")
            false
        } else if (binding.etLastName.text.toString().trim().length < 4) {
            Utility.showDialog(activity, "Last name must be at least 4 characters")
            false
        } else if (binding.etExpiry.text.toString().isEmpty()) {
            Utility.showDialog(activity, "Please enter expiry date")
            false
        } else if (binding.etCVV.text.toString().isEmpty() || binding.etCVV.text.length != 3) {
            Utility.showDialog(activity, "Please enter correct CVV")
            return false
        }
        else if (!isExpiryDateValid()) {
            Utility.showDialog(activity, "Card expiry date cannot be in the past")
            false
        } else {
            true
        }
    }

    private fun isExpiryDateValid(): Boolean {
        try {
            val expiryText = binding.etExpiry.text.toString()
            if (expiryText.isEmpty()) return false
            
            // Parse the expiry date (MM/yy format)
            val parts = expiryText.split("/")
            if (parts.size != 2) return false
            
            val month = parts[0].toInt()
            val year = parts[1].toInt()
            
            // Convert to full year (assuming 20xx)
            val fullYear = 2000 + year
            
            // Get current date
            val currentDate = Calendar.getInstance()
            val currentYear = currentDate.get(Calendar.YEAR)
            val currentMonth = currentDate.get(Calendar.MONTH) + 1 // Calendar.MONTH is 0-based
            
            // Compare year first, then month
            if (fullYear < currentYear) return false
            if (fullYear == currentYear && month < currentMonth) return false
            
            return true
        } catch (e: Exception) {
            Log.e("AddCardActivity", "Error validating expiry date: ${e.message}")
            return false
        }
    }

    private fun updateLabel() {
        val myFormat = "MM/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.etExpiry.setText(dateFormat.format(myCalendar.time))
    }

    private fun setupEditMode() {
        editingCard?.let { card ->
            // Update the heading
            binding.tvHeading.text = "Edit Card Details"
            
            // Update submit button text
            binding.btnSubmit.text = "Update Card"
            
            // Auto-fill card number (show last 4 digits with asterisks)
            val last4 = card.cardDetails?.last4 ?: "****"
            binding.etCardNumber.setText("**** **** **** $last4")
            binding.etCardNumber.isEnabled = false // Disable editing card number
            
            // Auto-fill name fields
            val fullName = card.cardDetails?.name ?: ""
            val nameParts = fullName.split(" ")
            if (nameParts.isNotEmpty()) {
                binding.etFirstName.setText(nameParts[0])
                if (nameParts.size > 1) {
                    binding.etLastName.setText(nameParts.drop(1).joinToString(" "))
                }
            }
            
            // Auto-fill expiry date
            val expMonth = card.cardDetails?.expMonth
            val expYear = card.cardDetails?.expYear
            if (expMonth != null && expYear != null) {
                val yearString = expYear.toString().takeLast(2) // Get last 2 digits
                val expiryText = String.format("%02d/%s", expMonth, yearString)
                binding.etExpiry.setText(expiryText)
            }
            
            // CVV field remains empty for security
            binding.etCVV.setText("")
        }
    }


    private fun addCardApi() {
        showWaitingDialog(this)
        val params: MutableMap<String, RequestBody> = HashMap()
        
        if (isEditMode) {
            // For edit mode, use the existing card number from the selected card
            val cardNumber = editingCard?.cardDetails?.last4 ?: ""
            params["card_number"] = Utility.getRequestParam("**** **** **** $cardNumber")
        } else {
            val digitsOnly = binding.etCardNumber.text.toString().replace("\\D".toRegex(), "")
            params["card_number"] = Utility.getRequestParam(digitsOnly)
        }
        
        params["exp"] = Utility.getRequestParam(binding.etExpiry.text.toString())
        params["cvv"] = Utility.getRequestParam(binding.etCVV.text.toString())
        params["brand"] = Utility.getRequestParam(binding.etFirstName.text.toString())
        params["name"] = Utility.getRequestParam((binding.etFirstName.text.toString() + " " + binding.etLastName.text.toString()))
        params["firstname"] = Utility.getRequestParam(binding.etFirstName.text.toString())
        params["lastname"] = Utility.getRequestParam(binding.etLastName.text.toString())

        // If editing, add the card ID for update
        if (isEditMode && editingCard?.id != null) {
            params["card_id"] = Utility.getRequestParam(editingCard!!.id.toString())
        }

        RestCaller(
            this,
            this,
            RetrofitClient.getInstance().createCreditCard(
                Preferences.getUserDetails(activity).authToken, params
            ),
            if (isEditMode) editCardRequestCode else addCardRequestCode
        )
    }

    override fun <T : Any?> onSuccess(response: Any?, reqCode: Int) {
        dismissWaitingDialog(this)
        if (reqCode == addCardRequestCode || reqCode == editCardRequestCode) {
            try {
                val responseData = JSONObject(Utility.convertToString(response))
                if (!responseData.getBoolean("error")) {
                    if (reqCode == addCardRequestCode) {
                        // Handle add card response
                        val newCard = if (responseData.has("data")) {
                            try {
                                val cardData = responseData.getJSONObject("data")
                                Card(
                                    id = cardData.optInt("id"),
                                    cardDetails = CardDetails(
                                        last4 = cardData.optString(
                                            "last4",
                                            binding.etCardNumber.text.toString().takeLast(4)
                                        ),
                                        name = binding.etFirstName.text.toString() + " " + binding.etLastName.text.toString(),
                                        expMonth = binding.etExpiry.text.toString()
                                            .split("/")[0].toIntOrNull(),
                                        expYear = "20" + binding.etExpiry.text.toString()
                                            .split("/")[1].toIntOrNull()
                                    ),
                                    isDefault = true
                                )
                            } catch (e: Exception) {
                                // Fallback to creating card from form data
                                Card(
                                    cardDetails = CardDetails(
                                        last4 = binding.etCardNumber.text.toString().takeLast(4),
                                        name = binding.etFirstName.text.toString() + " " + binding.etLastName.text.toString(),
                                        expMonth = binding.etExpiry.text.toString().split("/")[0].toIntOrNull(),
                                        expYear = "20" + binding.etExpiry.text.toString().split("/")[1].toIntOrNull()
                                    ),
                                    isDefault = true
                                )
                            }
                        } else {
                            // Create card from form data if no response data
                            Card(
                                cardDetails = CardDetails(
                                    last4 = binding.etCardNumber.text.toString().takeLast(4),
                                    name = binding.etFirstName.text.toString() + " " + binding.etLastName.text.toString(),
                                    expMonth = binding.etExpiry.text.toString().split("/")[0].toIntOrNull(),
                                    expYear = "20" + binding.etExpiry.text.toString().split("/")[1].toIntOrNull()
                                ),
                                isDefault = true
                            )
                        }
                        
                        // Set the newly added card as selected card

                        if(Preferences.getDefaultCard(activity) == null) {
//                            CarDetailActivity.selectedCard = newCard
                            Preferences.saveDefaultCard(activity, newCard)
                            // Update user model locally
                            val userModel = Preferences.getUserDetails(activity)
                            if (userModel != null) {
                                val defaultCard = UserModel.DefaultCard()
                                defaultCard.id = newCard.id
                                defaultCard.last4 = newCard.cardDetails?.last4 ?: ""
                                defaultCard.name = newCard.cardDetails?.name ?: ""
                                defaultCard.expMonth =
                                    newCard.cardDetails?.expMonth?.toString() ?: ""
                                defaultCard.expYear = newCard.cardDetails?.expYear?.toString() ?: ""
                                defaultCard.isDefault = true
                                userModel.defaultCard = defaultCard
                                Preferences.saveLoginDefaults(activity, userModel)
                            }
                        }
                    } else {
                        // Handle edit card response - update the existing card
                        editingCard?.let { card ->
                            val updatedCard = card.copy(
                                cardDetails = card.cardDetails?.copy(
                                    name = binding.etFirstName.text.toString() + " " + binding.etLastName.text.toString(),
                                    expMonth = binding.etExpiry.text.toString().split("/")[0].toIntOrNull(),
                                    expYear = "20" + binding.etExpiry.text.toString().split("/")[1].toIntOrNull()
                                )
                            )
                            //CarDetailActivity.selectedCard = updatedCard
                            Preferences.saveDefaultCard(activity, updatedCard)
                        }
                    }
                    
                    Utility.showSuccessDialog(activity, responseData.getString("message"), true)
                } else {
                    handleErrorMessage(responseData, activity)
                }
            } catch (e: Exception) {
                Log.e("JSON_ERROR", "" + e.message)
                Utility.showDialog(activity, e.message, false)
            }
        }
    }

    override fun onFailure(t: Throwable?, reqCode: Int) {
        onFailure(t.toString(), this)
    }

}