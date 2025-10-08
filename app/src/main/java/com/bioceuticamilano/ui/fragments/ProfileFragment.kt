package com.bioceuticamilano.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bioceuticamilano.NotificationActivity
import com.bioceuticamilano.R
import com.bioceuticamilano.databinding.FragmentProfileBinding
import com.bioceuticamilano.ui.activities.SavedCardsActivity
import com.bioceuticamilano.ui.activities.TrackOrderActivity
import com.bioceuticamilano.ui.activities.AddressListActivity
import com.bioceuticamilano.ui.activities.OrderHistoryActivity
import com.bioceuticamilano.ui.activities.ProfileDetailActivity
import com.bioceuticamilano.utils.Preferences

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userModel = Preferences.getUserDetails(requireContext())

        binding.tvName.text = userModel.fullName
        binding.tvEmail.text = userModel.email

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
        binding.root.findViewById<View>(R.id.inc_order_history)?.setOnClickListener {
            startActivity(Intent(context, OrderHistoryActivity::class.java))
        }
        binding.root.findViewById<View>(R.id.inc_payment_method)?.setOnClickListener {
            startActivity(Intent(context, SavedCardsActivity::class.java))
        }
        binding.root.findViewById<View>(R.id.inc_notifications)?.setOnClickListener {
            startActivity(Intent(context, NotificationActivity::class.java))
        }
        binding.root.findViewById<View>(R.id.inc_track_order)?.setOnClickListener {
            startActivity(Intent(context, TrackOrderActivity::class.java))
        }
        binding.root.findViewById<View>(R.id.inc_cart)?.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.host_fragment, CartFragment())
                addToBackStack(null)
            }
        }

        binding.root.findViewById<View>(R.id.inc_delivery_address)?.setOnClickListener {
            startActivity(Intent(context, AddressListActivity::class.java))
        }

        setIncludeLabel(R.id.inc_refund_policy, R.drawable.ic_privacy_policy, "Refund Policy")
        setIncludeLabel(R.id.inc_shipping_policy, R.drawable.ic_privacy_policy, "Shipping Policy")
        setIncludeLabel(R.id.inc_privacy_policy, R.drawable.ic_privacy_policy, "Privacy Policy")
        setIncludeLabel(R.id.inc_terms_conditions, R.drawable.ic_terms_conditions, "Terms and conditions")
        setIncludeLabel(R.id.inc_about_us, R.drawable.ic_about_us, "About Us")
        setIncludeLabel(R.id.inc_visit_website, R.drawable.ic_visit_website, "Visit Our Website")

        setIncludeLabel(R.id.inc_logout, R.drawable.ic_logout, "Log Out")
        setIncludeLabel(R.id.inc_delete_account, R.drawable.ic_delete_account, "Delete my account")

        // show delete account dialog
        binding.root.findViewById<View>(R.id.inc_delete_account)?.setOnClickListener {
            DeleteAccountDialogFragment().show(parentFragmentManager, "delete_dialog")
        }

        // show logout dialog
        binding.root.findViewById<View>(R.id.inc_logout)?.setOnClickListener {
            LogoutDialogFragment().show(parentFragmentManager, "logout_dialog")
        }

        // listen for results
        parentFragmentManager.setFragmentResultListener("delete_account", viewLifecycleOwner) { key, bundle ->
            val confirmed = bundle.getBoolean("confirmed", false)
            if (confirmed) {
                // TODO: perform account deletion
            }
        }
        parentFragmentManager.setFragmentResultListener("logout", viewLifecycleOwner) { key, bundle ->
            val confirmed = bundle.getBoolean("confirmed", false)
            if (confirmed) {
                // TODO: perform logout
            }
        }

        // open webview pages
        binding.root.findViewById<View>(R.id.inc_privacy_policy)?.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.host_fragment, WebViewFragment.Companion.newInstance("Privacy Policy", "https://www.bioceuticamilano.com/policies/privacy-policy"))
                addToBackStack(null)
            }
        }
        binding.root.findViewById<View>(R.id.inc_terms_conditions)?.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.host_fragment, WebViewFragment.Companion.newInstance("Terms & Conditions", "https://www.bioceuticamilano.com/policies/terms-of-service"))
                addToBackStack(null)
            }
        }
        binding.root.findViewById<View>(R.id.inc_refund_policy)?.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.host_fragment, WebViewFragment.Companion.newInstance("Refund Policy", "https://www.bioceuticamilano.com/policies/refund-policy"))
                addToBackStack(null)
            }
        }
        binding.root.findViewById<View>(R.id.inc_shipping_policy)?.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.host_fragment, WebViewFragment.Companion.newInstance("Shipping Policy", "https://www.bioceuticamilano.com/policies/shipping-policy"))
                addToBackStack(null)
            }
        }
        binding.root.findViewById<View>(R.id.inc_visit_website)?.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.host_fragment, WebViewFragment.Companion.newInstance("Our Website", "https://www.bioceuticamilano.com"))
                addToBackStack(null)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
