package com.bmit.fma.canteen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentViewOrderHistoryBinding
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.firebaseUtils.UpdateData
import com.bmit.fma.interfaceListener.InterfaceListener
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.student.HistoryItemAdapter
import com.bmit.fma.student.ListMenu
import com.bmit.fma.student.ListOrder
import com.bmit.fma.utils.Common

class CanteenOrderFragment: Fragment(), InterfaceListener, ItemCallback{
    private var _binding: FragmentViewOrderHistoryBinding? = null
    private val binding get() = _binding!!
    private val listOrder = mutableListOf<ListOrder>()
    private val getData = GetData()
    private val updateData = UpdateData()
    private lateinit var orderItemAdapter: OrderItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewOrderHistoryBinding.inflate(inflater, container, false)
        orderItemAdapter = OrderItemAdapter(listOrder, this@CanteenOrderFragment)

        binding.viewOrderRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CanteenOrderFragment.context)
            adapter = orderItemAdapter
        }

        getData.getOrderedList(this)
        return binding.root
    }

    override fun returnList(item: Collection<*>) {
        super.returnList(item)
        listOrder.clear()
        listOrder.addAll(item as Collection<ListOrder>)
        orderItemAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.logoutBtn.setOnClickListener {
            Common().logout(requireActivity())

        }
    }

    override fun onSelectOrder(itemId: String) {
        super.onSelectOrder(itemId)
        val bundle = bundleOf("id" to itemId)
        findNavController().navigate(R.id.action_canteenOrderFragment_to_canteenOrderDetailsFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}