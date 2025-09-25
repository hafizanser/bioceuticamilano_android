package com.bioceuticamilano.utils

import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bioceuticamilano.R

class CustomTimePickerDialog(
    context: Context,
    listener: TimePickerDialog.OnTimeSetListener?,
    hourOfDay: Int,
    minute: Int,
    is24HourView: Boolean
) : TimePickerDialog(context, R.style.CustomTimePickerDialog, listener, hourOfDay, minute, is24HourView) {

    companion object {
        private const val TAG = "CustomTimePickerDialog"
    }

    init {
        setupDialog()
    }

    private fun setupDialog() {
        try {
            // Set dialog properties
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            
            // Apply custom styling after dialog is shown
            setOnShowListener {
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    applyCustomStyling()
                }, 150)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in setupDialog: ${e.message}")
        }
    }

    private fun applyCustomStyling() {
        try {
            // Style the dialog window
            styleDialogWindow()
            
            // Find and style the TimePicker
            val timePicker = findTimePickerInDialog()
            timePicker?.let { styleTimePicker(it) }
            
            // Style dialog buttons
            styleDialogButtons()
            
            // Apply continuous styling to prevent reset
            applyContinuousStyling()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in applyCustomStyling: ${e.message}")
        }
    }

    private fun styleDialogWindow() {
        try {
            window?.let { window ->
                // Set dialog background
                window.setBackgroundDrawable(createDialogBackground())
                
                // Set dialog width and height
                val displayMetrics = context.resources.displayMetrics
                val width = (displayMetrics.widthPixels * 0.9).toInt()
                val height = ViewGroup.LayoutParams.WRAP_CONTENT
                window.setLayout(width, height)
                
                // Center the dialog
                window.setGravity(Gravity.CENTER)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in styleDialogWindow: ${e.message}")
        }
    }

    private fun createDialogBackground(): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16f
            setColor(ContextCompat.getColor(context, R.color.white))
            setStroke(2, ContextCompat.getColor(context, R.color.black))
        }
    }

    private fun findTimePickerInDialog(): android.widget.TimePicker? {
        try {
            val window = window
            if (window != null) {
                val decorView = window.decorView
                return findTimePickerInView(decorView)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in findTimePickerInDialog: ${e.message}")
        }
        return null
    }

    private fun findTimePickerInView(parent: View): android.widget.TimePicker? {
        try {
            if (parent is android.widget.TimePicker) {
                return parent
            }
            
            if (parent is ViewGroup) {
                for (i in 0 until parent.childCount) {
                    val child = parent.getChildAt(i)
                    val timePicker = findTimePickerInView(child)
                    if (timePicker != null) {
                        return timePicker
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in findTimePickerInView: ${e.message}")
        }
        return null
    }

    private fun styleTimePicker(timePicker: android.widget.TimePicker) {
        try {
            // Set background
            timePicker.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            
            // Disable animations
            disableAnimations(timePicker)
            
            // Style NumberPickers
            styleNumberPickers(timePicker)
            
            // Style AM/PM indicator
            styleAmPmIndicator(timePicker)
            
            // Add time change listener
            addTimeChangeListener(timePicker)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in styleTimePicker: ${e.message}")
        }
    }

    private fun styleNumberPickers(timePicker: android.widget.TimePicker) {
        try {
            val timePickerClass = timePicker.javaClass
            val fields = timePickerClass.declaredFields
            
            for (field in fields) {
                field.isAccessible = true
                val fieldValue = field.get(timePicker)
                
                if (fieldValue is NumberPicker) {
                    styleNumberPicker(fieldValue)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in styleNumberPickers: ${e.message}")
        }
    }

    private fun styleNumberPicker(numberPicker: NumberPicker) {
        try {
            // Set text color
            numberPicker.setTextColor(ContextCompat.getColor(context, R.color.black))
            
            // Disable animations
            numberPicker.clearAnimation()
            numberPicker.animate().cancel()
            
            // Style internal EditText
            styleNumberPickerEditText(numberPicker)
            
            // Style dividers
            styleNumberPickerDividers(numberPicker)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in styleNumberPicker: ${e.message}")
        }
    }

    private fun styleNumberPickerEditText(numberPicker: NumberPicker) {
        try {
            val pickerFields = NumberPicker::class.java.declaredFields
            for (field in pickerFields) {
                field.isAccessible = true
                val fieldValue = field.get(numberPicker)
                
                if (fieldValue is android.widget.EditText) {
                    val editText = fieldValue
                    editText.setTextColor(ContextCompat.getColor(context, R.color.black))
                    editText.setBackgroundColor(Color.TRANSPARENT)
                    editText.textSize = 20f
                    editText.gravity = Gravity.CENTER
                    editText.isFocusable = false
                    editText.isClickable = false
                    editText.clearAnimation()
                    editText.animate().cancel()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in styleNumberPickerEditText: ${e.message}")
        }
    }

    private fun styleNumberPickerDividers(numberPicker: NumberPicker) {
        try {
            // Use reflection to access divider fields
            val pickerFields = NumberPicker::class.java.declaredFields
            for (field in pickerFields) {
                field.isAccessible = true
                val fieldValue = field.get(numberPicker)
                
                if (fieldValue is android.graphics.drawable.Drawable) {
                    // This might be a divider drawable
                    if (field.name.contains("divider", ignoreCase = true)) {
                        // Create custom divider
                        val customDivider = createCustomDivider()
                        field.set(numberPicker, customDivider)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in styleNumberPickerDividers: ${e.message}")
        }
    }

    private fun createCustomDivider(): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(ContextCompat.getColor(context, R.color.black))
            setSize(0, 2)
        }
    }

    private fun styleAmPmIndicator(timePicker: android.widget.TimePicker) {
        try {
            // Find AM/PM elements in the entire dialog
            val window = window
            if (window != null) {
                val decorView = window.decorView
                findAndStyleAmPmElements(decorView)
            }
            
            // Also search in the time picker itself
            findAndStyleAmPmElements(timePicker)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in styleAmPmIndicator: ${e.message}")
        }
    }

    private fun findAndStyleAmPmElements(parent: View) {
        try {
            if (parent is TextView) {
                val text = parent.text.toString().uppercase()
                if (text == "AM" || text == "PM") {
                    styleAmPmElement(parent)
                }
            } else if (parent is Button) {
                val text = parent.text.toString().uppercase()
                if (text == "AM" || text == "PM") {
                    styleAmPmElement(parent)
                }
            }
            
            if (parent is ViewGroup) {
                for (i in 0 until parent.childCount) {
                    findAndStyleAmPmElements(parent.getChildAt(i))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in findAndStyleAmPmElements: ${e.message}")
        }
    }

    private fun styleAmPmElement(element: View) {
        try {
            // Use the same background as buttons for consistency
            val background = getButtonBackground()
            
            if (element is TextView) {
                element.background = background
                element.setTextColor(ContextCompat.getColor(context, R.color.white))
                element.textSize = 16f
                element.gravity = Gravity.CENTER
                element.setPadding(16, 8, 16, 8)
                element.setTypeface(null, android.graphics.Typeface.BOLD)
            } else if (element is Button) {
                element.background = background
                element.setTextColor(ContextCompat.getColor(context, R.color.white))
                element.textSize = 16f
                element.setPadding(16, 8, 16, 8)
                element.setTypeface(null, android.graphics.Typeface.BOLD)
            }
            
            // Ensure visibility
            element.visibility = View.VISIBLE
            element.clearAnimation()
            element.animate().cancel()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in styleAmPmElement: ${e.message}")
        }
    }

    private fun createAmPmBackground(): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 8f
            setColor(ContextCompat.getColor(context, R.color.black))
            setStroke(1, ContextCompat.getColor(context, R.color.black))
        }
    }

    private fun addTimeChangeListener(timePicker: android.widget.TimePicker) {
        try {
            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                // Re-apply AM/PM styling when time changes
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    styleAmPmIndicator(timePicker)
                }, 100)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in addTimeChangeListener: ${e.message}")
        }
    }

    private fun styleDialogButtons() {
        try {
            val window = window
            if (window != null) {
                val decorView = window.decorView
                findAndStyleButtons(decorView)
            }
            
            // Also try to find buttons by specific IDs
            try {
                val button1 = findViewById<View>(android.R.id.button1) // OK button
                val button2 = findViewById<View>(android.R.id.button2) // Cancel button
                val button3 = findViewById<View>(android.R.id.button3) // Neutral button
                
                button1?.let { styleDialogButton(it) }
                button2?.let { styleDialogButton(it) }
                button3?.let { styleDialogButton(it) }
            } catch (e: Exception) {
                Log.e(TAG, "Error finding buttons by ID: ${e.message}")
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in styleDialogButtons: ${e.message}")
        }
    }

    private fun findAndStyleButtons(parent: View) {
        try {
            if (parent is ViewGroup) {
                for (i in 0 until parent.childCount) {
                    val child = parent.getChildAt(i)
                    if (child is Button || 
                        (child is TextView && 
                         child.text.toString().lowercase() in listOf("ok", "cancel", "set", "done"))) {
                        styleDialogButton(child)
                    }
                    findAndStyleButtons(child)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in findAndStyleButtons: ${e.message}")
        }
    }

    private fun styleDialogButton(button: View) {
        try {
            val background = getButtonBackground()
            
            if (button is Button) {
                button.background = background
                button.setTextColor(ContextCompat.getColor(context, R.color.white))
                button.textSize = 16f
                button.setPadding(24, 12, 24, 12)
                button.setTypeface(null, android.graphics.Typeface.BOLD)
            } else if (button is TextView) {
                button.background = background
                button.setTextColor(ContextCompat.getColor(context, R.color.white))
                button.textSize = 16f
                button.setPadding(24, 12, 24, 12)
                button.setTypeface(null, android.graphics.Typeface.BOLD)
                button.gravity = Gravity.CENTER
            }
            
            // Add margins
            val layoutParams = button.layoutParams as? ViewGroup.MarginLayoutParams
            layoutParams?.let {
                it.setMargins(8, 8, 8, 8)
                button.layoutParams = it
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in styleDialogButton: ${e.message}")
        }
    }

    private fun createButtonBackground(): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 8f
            setColor(ContextCompat.getColor(context, R.color.black))
            setStroke(1, ContextCompat.getColor(context, R.color.black))
        }
    }
    
    private fun getButtonBackground(): android.graphics.drawable.Drawable {
        return ContextCompat.getDrawable(context, R.drawable.time_picker_button_bg) 
            ?: createButtonBackground()
    }

    private fun disableAnimations(view: View) {
        try {
            view.clearAnimation()
            view.animate().cancel()
            
            if (view is ViewGroup) {
                view.layoutTransition = null
                
                for (i in 0 until view.childCount) {
                    disableAnimations(view.getChildAt(i))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in disableAnimations: ${e.message}")
        }
    }

    private fun applyContinuousStyling() {
        try {
            var attempts = 0
            val maxAttempts = 10
            val handler = android.os.Handler(android.os.Looper.getMainLooper())
            val runnable = object : Runnable {
                override fun run() {
                    if (attempts < maxAttempts) {
                        val timePicker = findTimePickerInDialog()
                        timePicker?.let {
                            styleAmPmIndicator(it)
                        }
                        attempts++
                        handler.postDelayed(this, 200)
                    }
                }
            }
            handler.postDelayed(runnable, 200)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in applyContinuousStyling: ${e.message}")
        }
    }
} 