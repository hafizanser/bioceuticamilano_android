package com.bioceuticamilano

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bioceuticamilano.adapters.Testimonial
import com.bioceuticamilano.adapters.TestimonialAdapter
import com.bioceuticamilano.databinding.FragmentTestimonialsBinding

class TestimonialsFragment : Fragment() {

    private var _binding: FragmentTestimonialsBinding? = null
    private val binding get() = _binding!!

    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private val autoScrollIntervalMs = 4000L // 4 seconds
    private var isAutoScrollRunning = false

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val layoutManager = binding.rvTestimonials.layoutManager as? LinearLayoutManager ?: return
            val snapHelper = PagerSnapHelper() // we already attached one in onViewCreated, but creating local to find snap view is OK
            // find current snapped position
            val snapView = snapHelper.findSnapView(layoutManager) ?: layoutManager.findViewByPosition(layoutManager.findFirstVisibleItemPosition())
            val currentPos = if (snapView != null) layoutManager.getPosition(snapView) else layoutManager.findFirstVisibleItemPosition()
            val itemCount = binding.rvTestimonials.adapter?.itemCount ?: 0
            if (itemCount <= 1) return
            val next = (currentPos + 1) % itemCount
            binding.rvTestimonials.smoothScrollToPosition(next)
            autoScrollHandler.postDelayed(this, autoScrollIntervalMs)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTestimonialsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testimonials = listOf(
            Testimonial(
                "David J.",
                "5.0f",
                "I decided to try Voyafly's eSIM service during my road trip across Australia, and I'm glad I did. The eSIM...",
                R.drawable.testimonal_placeholder
            ),
            Testimonial(
                "Anna L.",
                "4.0f",
                "Good experience overall.",
                R.drawable.testimonal_placeholder
            ),
            Testimonial("Mark K.", "4.5f", "Helpful support and easy setup.", R.drawable.testimonal_placeholder),
            Testimonial("Sara P.", "5.0f", "Loved it!", R.drawable.testimonal_placeholder)
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

        // pause auto-scroll while user touches the RecyclerView
        binding.rvTestimonials.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> stopAutoScroll()
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> startAutoScroll()
            }
            // allow RecyclerView to handle the touch as well
            false
        }

        binding.rvTestimonials.post { setSelectedIndicator(0) }

        // start auto-scroll
        startAutoScroll()
    }

    private fun startAutoScroll() {
        if (isAutoScrollRunning) return
        isAutoScrollRunning = true
        autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollIntervalMs)
    }

    private fun stopAutoScroll() {
        if (!isAutoScrollRunning) return
        isAutoScrollRunning = false
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
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

    override fun onPause() {
        super.onPause()
        stopAutoScroll()
    }

    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll()
        _binding = null
    }
}
