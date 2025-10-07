package com.bioceuticamilano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrowseFragment : Fragment() {

    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_browse, container, false)
        recycler = root.findViewById(R.id.rvCategories)

        // sample data that matches your designs
        val items = listOf(
            CategoryItem("Best Sellers", R.drawable.ic_logo_item_view),
            CategoryItem("New Arrivals", R.drawable.ic_logo_item_view),
            CategoryItem("Anti_Aging", R.drawable.ic_logo_item_view),
            CategoryItem("Hydration", R.drawable.ic_logo_item_view),
            CategoryItem("Brightening", R.drawable.ic_logo_item_view),
            CategoryItem("Skincare Quiz", R.drawable.ic_logo_item_view)
        )

        val adapter = CategoryAdapter(items) { item ->
            // click - for now show toast, later navigate to category page
            Toast.makeText(requireContext(), "Open: ${item.title}", Toast.LENGTH_SHORT).show()
        }

        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.addItemDecoration(SpaceItemDecoration(8))
        recycler.adapter = adapter

        // search interaction placeholder
        val et = root.findViewById<android.widget.EditText>(R.id.etSearch)
        et.setOnEditorActionListener { v, actionId, _ ->
            // placeholder: show toast with search text
            Toast.makeText(requireContext(), "Search: ${v.text}", Toast.LENGTH_SHORT).show()
            true
        }

        return root
    }
}

// simple item decoration for spacing
class SpaceItemDecoration(private val dp: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: android.graphics.Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val d = (dp * view.resources.displayMetrics.density).toInt()
        outRect.set(d, d, d, d)
    }
}

data class CategoryItem(val title: String, val imageRes: Int)
