package com.bioceuticamilano

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bioceuticamilano.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.text = "John Doe"
        binding.tvEmail.text = "bioceuticalsmilan@gmail.com"

        // helper: function to set label and icon inside an include
        fun setIncludeLabel(includeId: Int, includeRes: Int, label: String) {
            val inc = binding.root.findViewById<View>(includeId)
            val labelTv = inc?.findViewById<TextView>(R.id.tvLabel)
            labelTv?.text = label
            val icon = inc?.findViewById<ImageView>(R.id.ivIcon)
            icon?.setImageResource(includeRes)
        }

        // use placeholder icon for now; replace with specific drawables when available
        val placeholder = R.drawable.ic_circle_placeholder

        setIncludeLabel(R.id.inc_personal_info, R.drawable.ic_profile, "Personal Info")
        setIncludeLabel(R.id.inc_setting, R.drawable.ic_settings, "Setting")

        // open Personal Info screen when the personal info row is clicked
        binding.root.findViewById<View>(R.id.inc_personal_info)?.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileDetailActivity::class.java))
        }

        setIncludeLabel(R.id.inc_cart, R.drawable.ic_cart_pro, "Cart")
        setIncludeLabel(R.id.inc_delivery_address, R.drawable.ic_delivery, "Delivery Address")
        setIncludeLabel(R.id.inc_order_history, R.drawable.ic_order, "Order History")
        setIncludeLabel(R.id.inc_track_order, R.drawable.ic_track, "Track Order")
        setIncludeLabel(R.id.inc_payment_method, R.drawable.ic_payment, "Payment Method")
        setIncludeLabel(R.id.inc_favourite, R.drawable.ic_favourit, "Favourite")
        setIncludeLabel(R.id.inc_notifications, R.drawable.ic_notifications, "Notifications")
        setIncludeLabel(R.id.inc_support, R.drawable.ic_support, "Support")

        // open Support screen when support row is clicked
        binding.root.findViewById<View>(R.id.inc_support)?.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.host_fragment, SupportFragment())
                addToBackStack(null)
            }
        }

        setIncludeLabel(R.id.inc_privacy_policy, R.drawable.ic_privacy_policy, "Privacy Policy")
        setIncludeLabel(R.id.inc_terms_conditions, R.drawable.ic_terms_conditions, "Terms and conditions")
        setIncludeLabel(R.id.inc_about_us, R.drawable.ic_about_us, "About Us")
        setIncludeLabel(R.id.inc_visit_website, R.drawable.ic_visit_website, "Visit Our Website")

        setIncludeLabel(R.id.inc_logout, R.drawable.ic_logout, "Log Out")
        setIncludeLabel(R.id.inc_delete_account, R.drawable.ic_delete_account, "Delete my account")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
