package com.bioceuticamilano

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bioceuticamilano.databinding.ActivityAddressListBinding

data class Address(
    val id: Int,
    val label: String,
    val name: String,
    val phone: String,
    val fullAddress: String,
    val isDefault: Boolean = false
)

class AddressListActivity : AppCompatActivity() {

    private var _binding: ActivityAddressListBinding? = null
    private val binding get() = _binding!!

    private val addresses = mutableListOf<Address>()
    private lateinit var adapter: AddressAdapter
    private var nextId = 1

    private val addEditLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult
            val id = data.getIntExtra("id", -1)
            val label = data.getStringExtra("label") ?: "Home"
            val name = data.getStringExtra("name") ?: ""
            val phone = data.getStringExtra("phone") ?: ""
            val fullAddress = data.getStringExtra("fullAddress") ?: ""
            val isDefault = data.getBooleanExtra("isDefault", false)

            if (id >= 0) {
                // edit existing
                val idx = addresses.indexOfFirst { it.id == id }
                if (idx >= 0) {
                    addresses[idx] = Address(id, label, name, phone, fullAddress, isDefault)
                    adapter.notifyItemChanged(idx)
                    return@registerForActivityResult
                }
            }

            // new
            val new = Address(nextId++, label, name, phone, fullAddress, isDefault)
            addresses.add(0, new)
            adapter.notifyItemInserted(0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // sample prefilled item (optional)
        addresses.add(Address(nextId++, "HOME", "John Doe", "+966-99-1067004", "Al aidah madinah, almonoara, Madinah province 43314, Madinah, Saudia Arabia", true))

        binding.rvAddresses.layoutManager = LinearLayoutManager(this)
        adapter = AddressAdapter(addresses,
            onEdit = { pos ->
                val addr = addresses[pos]
                val i = Intent(this, AddressEditActivity::class.java).apply {
                    putExtra("id", addr.id)
                    putExtra("label", addr.label)
                    putExtra("name", addr.name)
                    putExtra("phone", addr.phone)
                    putExtra("fullAddress", addr.fullAddress)
                    putExtra("isDefault", addr.isDefault)
                }
                addEditLauncher.launch(i)
            }
        )
        binding.rvAddresses.adapter = adapter

        binding.btnAddNewAddress.setOnClickListener {
            val i = Intent(this, AddressEditActivity::class.java)
            addEditLauncher.launch(i)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    class AddressAdapter(
        private val items: MutableList<Address>,
        private val onEdit: (Int) -> Unit
    ) : RecyclerView.Adapter<AddressAdapter.VH>() {

        inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvLabel: TextView = itemView.findViewById(R.id.tvLabel)
            val tvName: TextView = itemView.findViewById(R.id.tvName)
            val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
            val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
            val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
            return VH(v)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val a = items[position]
            holder.tvLabel.text = a.label
            holder.tvName.text = a.name
            holder.tvAddress.text = a.fullAddress
            holder.tvPhone.text = a.phone
            holder.btnEdit.setOnClickListener { onEdit(position) }
        }

        override fun getItemCount(): Int = items.size
    }
}
