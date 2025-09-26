package com.bioceuticamilano

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bioceuticamilano.databinding.FragmentTestimonialsBinding

class TestimonialsFragment : Fragment() {

    private var _binding: FragmentTestimonialsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTestimonialsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testimonials = listOf(
            Testimonial("David J.", 5.0f, "I decided to try Voyafly's eSIM service during my road trip across Australia, and I'm glad I did. The eSIM...", R.drawable.ic_profile_placeholder),
            Testimonial("Anna L.", 4.0f, "Good experience overall.", R.drawable.ic_profile_placeholder),
            Testimonial("Mark K.", 4.5f, "Helpful support and easy setup.", R.drawable.ic_profile_placeholder),
            Testimonial("Sara P.", 5.0f, "Loved it!", R.drawable.ic_profile_placeholder)
        )

        val adapter = TestimonialAdapter(testimonials)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTestimonials.layoutManager = layoutManager
        binding.rvTestimonials.adapter = adapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvTestimonials)

        setupIndicatorDots(testimonials.size)

        binding.rvTestimonials.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
                super.onScrollStateChanged(rv, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val snapView = snapHelper.findSnapView(layoutManager)
                    val position = if (snapView != null) layoutManager.getPosition(snapView) else 0
                    setSelectedIndicator(position)
                }
            }
        })

        binding.rvTestimonials.post { setSelectedIndicator(0) }
    }

    private fun setupIndicatorDots(count: Int) {
        binding.llIndicator.removeAllViews()
        val ctx = requireContext()
        val size = (8 * ctx.resources.displayMetrics.density).toInt()
        val margin = (8 * ctx.resources.displayMetrics.density).toInt()
        for (i in 0 until count) {
            val dot = View(requireContext())
            val params = LinearLayout.LayoutParams(size * 2, size)
            params.marginStart = margin
            params.marginEnd = margin
            dot.layoutParams = params
            dot.background = createDotDrawable(false)
            binding.llIndicator.addView(dot)
        }
    }

    private fun setSelectedIndicator(index: Int) {
        for (i in 0 until binding.llIndicator.childCount) {
            val v = binding.llIndicator.getChildAt(i)
            v.background = createDotDrawable(i == index)
        }
    }

    private fun createDotDrawable(isActive: Boolean) = GradientDrawable().apply {
        cornerRadius = 6f * resources.displayMetrics.density
        setColor(if (isActive) ContextCompat.getColor(requireContext(), R.color.primary_pink) else ContextCompat.getColor(requireContext(), R.color.light_gray_color))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
