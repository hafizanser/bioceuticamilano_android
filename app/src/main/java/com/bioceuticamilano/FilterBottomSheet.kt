package com.bioceuticamilano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup

class FilterBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false)

        // Wire groups so AppCompatTextView children act like single-selection chips
        fun wireGroup(groupId: Int) {
            val group = view.findViewById<ChipGroup>(groupId)
            for (i in 0 until group.childCount) {
                val child = group.getChildAt(i)
                child.isClickable = true
                child.isFocusable = true
                child.setOnClickListener {
                    // clear selection in this group
                    for (j in 0 until group.childCount) group.getChildAt(j).isSelected = false
                    // select clicked
                    it.isSelected = true
                }
            }
        }

        wireGroup(R.id.chipGroupSort)
        wireGroup(R.id.chipGroupPrice)
        wireGroup(R.id.chipGroupSkin)

        // Reset button clears selections
        val btnReset = view.findViewById<MaterialButton>(R.id.btnReset)
        btnReset.setOnClickListener {
            listOf(R.id.chipGroupSort, R.id.chipGroupPrice, R.id.chipGroupSkin).forEach { gid ->
                val group = view.findViewById<ChipGroup>(gid)
                for (i in 0 until group.childCount) group.getChildAt(i).isSelected = false
            }
        }

        val ivCross = view.findViewById<ImageView>(R.id.ivCross)
        ivCross.setOnClickListener {
            dismiss()
        }

        // Apply button collects selected values and sends result back
        val btnApply = view.findViewById<View>(R.id.btnApply)
        btnApply.setOnClickListener {
            val selectedSort = getSelectedText(view, R.id.chipGroupSort)
            val selectedPrice = getSelectedText(view, R.id.chipGroupPrice)
            val selectedSkin = getSelectedText(view, R.id.chipGroupSkin)

            val result = bundleOf(
                "sort" to selectedSort,
                "price" to selectedPrice,
                "skin" to selectedSkin
            )
            parentFragmentManager.setFragmentResult("filter_applied", result)
            dismiss()
        }

        return view
    }

    private fun getSelectedText(root: View, groupId: Int): String? {
        val group = root.findViewById<ChipGroup>(groupId)
        for (i in 0 until group.childCount) {
            val child = group.getChildAt(i)
            if (child.isSelected && child is AppCompatTextView) return child.text.toString()
        }
        return null
    }
}
