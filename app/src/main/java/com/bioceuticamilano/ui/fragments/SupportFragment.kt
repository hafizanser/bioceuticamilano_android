package com.bioceuticamilano.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bioceuticamilano.R
import com.bioceuticamilano.databinding.FragmentSupportBinding
import com.bioceuticamilano.ui.activities.SupportDetailActivity

class SupportFragment : Fragment() {
    private var _binding: FragmentSupportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSupportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use resources/texts
        binding.tvTitle.text = getString(R.string.support_title)
        binding.tvSubtitle.text = getString(R.string.support_subtitle)

        // find included rows by id and set texts/icons
        val p = view.findViewById<View>(R.id.incProblemsRoot)
        p?.findViewById<TextView>(R.id.tvLabel)?.text = "Problems with the order"
        p?.findViewById<TextView>(R.id.tvDesc)?.text = "Need help with you order?"
        p?.findViewById<ImageView>(R.id.ivIcon)?.setImageResource(R.drawable.ic_support_info)

        val w = view.findViewById<View>(R.id.incWhereRoot)
        w?.findViewById<TextView>(R.id.tvLabel)?.text = "Where is my package?"
        w?.findViewById<TextView>(R.id.tvDesc)?.text = "Tracking, delivers and shipping issues"
        w?.findViewById<ImageView>(R.id.ivIcon)?.setImageResource(R.drawable.ic_support_question)

        val n = view.findViewById<View>(R.id.incNotSatisfiedRoot)
        n?.findViewById<TextView>(R.id.tvLabel)?.text = "I'm not satisfied"
        n?.findViewById<TextView>(R.id.tvDesc)?.text = "Returns, refunds and product problems"
        n?.findViewById<ImageView>(R.id.ivIcon)?.setImageResource(R.drawable.ic_support_unsatisfied)

        val info = view.findViewById<View>(R.id.incProductInfoRoot)
        info?.findViewById<TextView>(R.id.tvLabel)?.text = "Product information"
        info?.findViewById<TextView>(R.id.tvDesc)?.text = "Questions about our Product and treatments"
        info?.findViewById<ImageView>(R.id.ivIcon)?.setImageResource(R.drawable.ic_support_info)

        val o = view.findViewById<View>(R.id.incOtherRoot)
        o?.findViewById<TextView>(R.id.tvLabel)?.text = "Other"
        o?.findViewById<TextView>(R.id.tvDesc)?.text = "Other general questions and requests"
        o?.findViewById<ImageView>(R.id.ivIcon)?.setImageResource(R.drawable.ic_support_question)

        // example click to close or navigate
        // start SupportDetailActivity and pass the selected type so activity can show the correct content
        p?.setOnClickListener {
            val intent = Intent(requireContext(), SupportDetailActivity::class.java)
            intent.putExtra("support_type", "problems")
            startActivity(intent)
        }
        w?.setOnClickListener {
            val intent = Intent(requireContext(), SupportDetailActivity::class.java)
            intent.putExtra("support_type", "where")
            startActivity(intent)
        }
        n?.setOnClickListener {
            val intent = Intent(requireContext(), SupportDetailActivity::class.java)
            intent.putExtra("support_type", "unsatisfied")
            startActivity(intent)
        }
        info?.setOnClickListener {
            val intent = Intent(requireContext(), SupportDetailActivity::class.java)
            intent.putExtra("support_type", "product_info")
            startActivity(intent)
        }
        o?.setOnClickListener {
            val intent = Intent(requireContext(), SupportDetailActivity::class.java)
            intent.putExtra("support_type", "other")
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        // hide bottom navigation when this fragment is visible
        requireActivity().findViewById<View>(R.id.bottom_navigation)?.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        // restore bottom navigation visibility when leaving
        requireActivity().findViewById<View>(R.id.bottom_navigation)?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
