package com.bioceuticamilano.ui.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.bioceuticamilano.databinding.DialogDeleteAccountBinding

class DeleteAccountDialogFragment : BottomSheetDialogFragment() {
    private var _binding: DialogDeleteAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogDeleteAccountBinding.inflate(inflater, container, false)
        isCancelable = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDelete.setOnClickListener {
            parentFragmentManager.setFragmentResult("delete_account", bundleOf("confirmed" to true))
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        // make bottom sheet full width and expanded
        (dialog as? BottomSheetDialog)?.let { d ->
            d.window?.setBackgroundDrawableResource(R.color.transparent)
            val sheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            sheet?.layoutParams?.width = LayoutParams.MATCH_PARENT
            sheet?.layoutParams?.height = LayoutParams.WRAP_CONTENT
            sheet?.requestLayout()
            d.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
