package com.bioceuticamilano.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bioceuticamilano.R
import com.bioceuticamilano.TestimonialsFragment
import com.bioceuticamilano.adapters.Product
import com.bioceuticamilano.adapters.ProductAdapter
import com.bioceuticamilano.adapters.VideoPagerAdapter
import com.bioceuticamilano.databinding.FragmentHomeBinding
import kotlin.math.abs

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.ivProfile.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.host_fragment, ProfileFragment())
//                .addToBackStack(null)
//                .commit()
//        }

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
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
            "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"
        )

        // setup ViewPager2 adapter
        val adapter = VideoPagerAdapter(requireContext(), sampleVideos, binding.vpVideos)
        binding.vpVideos.adapter = adapter
        binding.vpVideos.offscreenPageLimit = 3

        // preload players to avoid prepare jank during swipes
        adapter.preloadAll()

        // set initial centered item immediately (middle index) so transformer reacts correctly
        val initialIndex = if ((binding.vpVideos.adapter?.itemCount ?: 0) > 1) 1 else 0
        binding.vpVideos.setCurrentItem(initialIndex, false)
        (binding.vpVideos.getChildAt(0) as? RecyclerView)?.scrollToPosition(initialIndex)
        adapter.playAt(initialIndex)

        // Use CompositePageTransformer: margin + scaling so center page is large and sides peek smaller
        val compositeTransformer = CompositePageTransformer()
        // small margin between pages (in px)
        compositeTransformer.addTransformer(MarginPageTransformer(32))
        compositeTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            val scale = 0.85f + r * 0.15f
            page.scaleY = scale
            page.alpha = 0.7f + r * 0.3f
        }
        binding.vpVideos.setPageTransformer(compositeTransformer)

        // allow side pages to peek by disabling clipping on the internal RecyclerView
        val recyclerView = binding.vpVideos.getChildAt(0) as? RecyclerView
        recyclerView?.apply {
            clipToPadding = false
            clipChildren = false
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            // optional: increase start/end padding so side items are visible; values in px
            val pad = (resources.displayMetrics.density * 36).toInt()
            setPadding(pad, paddingTop, pad, paddingBottom)
        }

        // initial item already set above to avoid transformer/attachment race

        // play/pause handling on page change
        binding.vpVideos.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                adapter.playAt(position)
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
        (binding.vpVideos.adapter as? VideoPagerAdapter)?.releaseAll()
        _binding = null
    }
}
