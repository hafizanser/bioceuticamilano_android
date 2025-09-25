package com.bioceuticamilano.utils

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import com.bioceuticamilano.R
import java.util.*

class CustomDatePickerDialog(
    context: Context,
    listener: DatePickerDialog.OnDateSetListener,
    year: Int,
    month: Int,
    dayOfMonth: Int
) : DatePickerDialog(context, R.style.CustomDatePickerDialog, listener, year, month, dayOfMonth) {

    init {
        setupCustomStyling()
        setupDateValidation()
    }

    private fun setupDateValidation() {
        try {
            // Set minimum date to today to prevent selecting past dates
            val calendar = Calendar.getInstance()
            datePicker.minDate = calendar.timeInMillis
            
            // Set maximum date to 10 years from now (reasonable for card expiry)
            calendar.add(Calendar.YEAR, 10)
            datePicker.maxDate = calendar.timeInMillis
            
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in setupDateValidation: ${e.message}")
        }
    }

    private fun setupCustomStyling() {
        try {
            // Set dialog background
            window?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.datePickerBackground)))
            
            // Apply custom styling after dialog is shown
            setOnShowListener {
                // Add a small delay to ensure the dialog is fully rendered
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    applyCustomDatePickerStyling()
                }, 100)
            }
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in setupCustomStyling: ${e.message}")
        }
    }

    private fun applyCustomDatePickerStyling() {
        try {
            val datePicker = datePicker
            
            // Set background color
            datePicker.setBackgroundColor(ContextCompat.getColor(context, R.color.datePickerBackground))
            
            // Apply custom styling to NumberPickers (year, month, day)
            applyNumberPickerStyling(datePicker)
            
            // Style the dialog buttons (OK and Cancel)
            styleDialogButtons()
            
            // Style the button container for better spacing
            styleButtonContainer()
            
            // For API 21+, we can set more specific colors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                datePicker.setBackgroundColor(ContextCompat.getColor(context, R.color.datePickerBackground))
            }
            
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in applyCustomDatePickerStyling: ${e.message}")
        }
    }

    private fun applyNumberPickerStyling(datePicker: DatePicker) {
        try {
            // Get the NumberPicker fields using reflection
            val fields = DatePicker::class.java.declaredFields
            
            for (field in fields) {
                if (field.type == NumberPicker::class.java) {
                    field.isAccessible = true
                    val numberPicker = field.get(datePicker) as NumberPicker
                    styleNumberPicker(numberPicker)
                }
            }
            
            // Also try to find NumberPickers in the DatePicker's children
            findAndStyleNumberPickers(datePicker)
            
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in applyNumberPickerStyling: ${e.message}")
        }
    }

    private fun findAndStyleNumberPickers(parent: View) {
        try {
            if (parent is NumberPicker) {
                styleNumberPicker(parent)
            }
            
            if (parent is android.view.ViewGroup) {
                for (i in 0 until parent.childCount) {
                    findAndStyleNumberPickers(parent.getChildAt(i))
                }
            }
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in findAndStyleNumberPickers: ${e.message}")
        }
    }

    private fun styleNumberPicker(numberPicker: NumberPicker) {
        try {
            // Set text color
            numberPicker.setTextColor(ContextCompat.getColor(context, R.color.datePickerText))
            
            // Set background color
            numberPicker.setBackgroundColor(ContextCompat.getColor(context, R.color.datePickerBackground))
            
            // For API 23+, we can set more specific colors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                numberPicker.setTextColor(ContextCompat.getColor(context, R.color.datePickerText))
            }
            
            // Try to style the NumberPicker's internal components
            styleNumberPickerInternal(numberPicker)
            
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in styleNumberPicker: ${e.message}")
        }
    }

    private fun styleNumberPickerInternal(numberPicker: NumberPicker) {
        try {
            // Get the NumberPicker's internal fields
            val pickerFields = NumberPicker::class.java.declaredFields
            
            for (field in pickerFields) {
                field.isAccessible = true
                
                when (field.name) {
                    "mInputText" -> {
                        val inputText = field.get(numberPicker) as? android.widget.EditText
                        inputText?.let {
                            it.setTextColor(ContextCompat.getColor(context, R.color.datePickerText))
                            it.setBackgroundColor(ContextCompat.getColor(context, R.color.datePickerBackground))
                        }
                    }
                    "mSelectorWheelPaint" -> {
                        val paint = field.get(numberPicker) as? android.graphics.Paint
                        paint?.let {
                            it.color = ContextCompat.getColor(context, R.color.datePickerText)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in styleNumberPickerInternal: ${e.message}")
        }
    }

    private fun styleDialogButtons() {
        try {
            // Find and style the dialog buttons
            val dialog = this
            
            // Get the dialog's window and find buttons
            val window = dialog.window
            if (window != null) {
                val decorView = window.decorView
                findAndStyleButtons(decorView)
            }
            
            // Also try to find buttons in the dialog's content view
            val contentView = dialog.findViewById<View>(android.R.id.content)
            if (contentView != null) {
                findAndStyleButtons(contentView)
            }
            
            // Try to find buttons by specific IDs (common DatePickerDialog button IDs)
            try {
                val button1 = dialog.findViewById<View>(android.R.id.button1) // OK button
                val button2 = dialog.findViewById<View>(android.R.id.button2) // Cancel button
                val button3 = dialog.findViewById<View>(android.R.id.button3) // Neutral button
                
                button1?.let { styleButton(it) }
                button2?.let { styleButton(it) }
                button3?.let { styleButton(it) }
            } catch (e: Exception) {
                Log.e("CustomDatePicker", "Error finding buttons by ID: ${e.message}")
            }
            
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in styleDialogButtons: ${e.message}")
        }
    }

    private fun findAndStyleButtons(parent: View) {
        try {
            if (parent is android.widget.Button) {
                styleButton(parent)
            } else if (parent is android.widget.TextView && parent.text.toString().lowercase() in listOf("ok", "cancel", "set", "done")) {
                styleButton(parent)
            }
            
            if (parent is android.view.ViewGroup) {
                for (i in 0 until parent.childCount) {
                    findAndStyleButtons(parent.getChildAt(i))
                }
            }
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in findAndStyleButtons: ${e.message}")
        }
    }

    private fun styleButton(button: View) {
        try {
            // Set text color to white for visibility on black background
            if (button is android.widget.Button) {
                button.setTextColor(ContextCompat.getColor(context, R.color.white))
                button.setBackgroundResource(R.drawable.date_picker_button_bg)
                button.textSize = 16f
                button.setPadding(24, 12, 24, 12)
                
                // Add margins around the button
                val layoutParams = button.layoutParams as? android.view.ViewGroup.MarginLayoutParams
                layoutParams?.let {
                    it.setMargins(16, 8, 16, 8)
                    button.layoutParams = it
                }
            } else if (button is android.widget.TextView) {
                button.setTextColor(ContextCompat.getColor(context, R.color.white))
                button.setBackgroundResource(R.drawable.date_picker_button_bg)
                button.textSize = 16f
                button.setPadding(24, 12, 24, 12)
                
                // Add margins around the button
                val layoutParams = button.layoutParams as? android.view.ViewGroup.MarginLayoutParams
                layoutParams?.let {
                    it.setMargins(16, 8, 16, 8)
                    button.layoutParams = it
                }
            }
            
            // Set text size for better visibility
            if (button is android.widget.TextView) {
                button.textSize = 16f
            }
            
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in styleButton: ${e.message}")
        }
    }

    private fun styleButtonContainer() {
        try {
            // Find and style the button container (usually a LinearLayout or FrameLayout)
            val dialog = this
            val window = dialog.window
            if (window != null) {
                val decorView = window.decorView
                findAndStyleButtonContainer(decorView)
            }
            
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in styleButtonContainer: ${e.message}")
        }
    }

    private fun findAndStyleButtonContainer(parent: View) {
        try {
            if (parent is android.view.ViewGroup) {
                // Check if this container has buttons as children
                if (parent.childCount > 0) {
                    var hasButtons = false
                    for (i in 0 until parent.childCount) {
                        val child = parent.getChildAt(i)
                        if (child is android.widget.Button || 
                            (child is android.widget.TextView && 
                             child.text.toString().lowercase() in listOf("ok", "cancel", "set", "done"))) {
                            hasButtons = true
                            break
                        }
                    }
                    
                    if (hasButtons) {
                        // This is likely the button container
                        if (parent is android.widget.LinearLayout) {
                            parent.orientation = android.widget.LinearLayout.HORIZONTAL
                            parent.gravity = android.view.Gravity.END
                        }
                        
                        // Add padding to the container
                        parent.setPadding(16, 16, 16, 16)
                        
                        // Set background if needed
                        parent.setBackgroundColor(ContextCompat.getColor(context, R.color.datePickerBackground))
                    }
                }
                
                // Continue searching in child views
                for (i in 0 until parent.childCount) {
                    findAndStyleButtonContainer(parent.getChildAt(i))
                }
            }
        } catch (e: Exception) {
            Log.e("CustomDatePicker", "Error in findAndStyleButtonContainer: ${e.message}")
        }
    }
} 