package com.bioceuticamilano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ResendOtpBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_resend_otp_bottom_sheet, container, false)
        val btn = view.findViewById<Button>(R.id.btnResendContinue)
        btn.setOnClickListener {
            // dismiss and optionally navigate
            dismiss()
        }
        return view
    }

    companion object {
        fun show(activity: FragmentActivity) {
            ResendOtpBottomSheet().show(activity.supportFragmentManager, "resend_otp")
        }
    }
}
