package com.bioceuticamilano

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bioceuticamilano.adapters.Product
import com.bioceuticamilano.adapters.ProductAdapter
import com.bioceuticamilano.adapters.VideoPagerAdapter
import com.bioceuticamilano.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivProfile.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationActivity::class.java))
        }

        val products = listOf(
            Product("BioInfusion+ | Microinfusion System", "$79 USD", "$99 USD", R.drawable.ic_product_placeholder),
            Product("BioInfusion+ | Microinfusion System", "$79 USD", "$99 USD", R.drawable.ic_product_placeholder),
            Product("BioInfusion+ | Microinfusion System", "$79 USD", "$99 USD", R.drawable.ic_product_placeholder)
        )

        binding.rvFeatured.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeatured.adapter = ProductAdapter(products)

        // Video carousel: ViewPager2 setup
        val sampleVideos = listOf(
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"
        )

        // setup ViewPager2 adapter
        val adapter = VideoPagerAdapter(requireContext(), sampleVideos, binding.vpVideos)
        binding.vpVideos.adapter = adapter
        binding.vpVideos.offscreenPageLimit = 3

        // Use CompositePageTransformer: margin + scaling so center page is large and sides peek smaller
        val compositeTransformer = androidx.viewpager2.widget.CompositePageTransformer()
        // small margin between pages (in px)
        compositeTransformer.addTransformer(androidx.viewpager2.widget.MarginPageTransformer(32))
        compositeTransformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            val scale = 0.85f + r * 0.15f
            page.scaleY = scale
            page.alpha = 0.7f + r * 0.3f
        }
        binding.vpVideos.setPageTransformer(compositeTransformer)

        // allow side pages to peek by disabling clipping on the internal RecyclerView
        val recyclerView = binding.vpVideos.getChildAt(0) as? androidx.recyclerview.widget.RecyclerView
        recyclerView?.apply {
            clipToPadding = false
            clipChildren = false
            overScrollMode = androidx.recyclerview.widget.RecyclerView.OVER_SCROLL_NEVER
            // optional: increase start/end padding so side items are visible; values in px
            val pad = (resources.displayMetrics.density * 36).toInt()
            setPadding(pad, paddingTop, pad, paddingBottom)
        }

        // start playing first page
        binding.vpVideos.post {
            binding.vpVideos.setCurrentItem(0, false)
        }

        // play/pause handling on page change
        binding.vpVideos.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.vpVideos.adapter?.let { adapterObj ->
                    if (adapterObj is com.bioceuticamilano.adapters.VideoPagerAdapter) {
                        // force rebind to update play state
                        adapterObj.notifyDataSetChanged()
                    }
                }
            }
        })

        // keep rvVideos visible if you want both; otherwise hide
        binding.rvVideos.visibility = View.GONE

        // add TestimonialsFragment inside the container
        childFragmentManager.beginTransaction()
            .replace(R.id.testimonialsContainer, TestimonialsFragment())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // release any players held by adapter
        (binding.vpVideos.adapter as? com.bioceuticamilano.adapters.VideoPagerAdapter)?.releaseAll()
        _binding = null
    }
}
