package com.bmit.fma.canteen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.databinding.FragmentCanteenOrderDetailsBinding
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.firebaseUtils.UpdateData
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.student.ListMenu
import com.bmit.fma.student.ListOrder
import com.bmit.fma.student.OrderSummaryAdapter
import com.bmit.fma.utils.Common
import com.google.gson.Gson
import java.util.*

class CanteenOrderDetailsFragment : Fragment(), ItemCallback {

    private var _binding : FragmentCanteenOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private val orderSummary = mutableListOf<ListOrder>()
    private val getData = GetData()
    private val updateData = UpdateData()
    private lateinit var orderId: String
    private lateinit var status : String
    private lateinit var studentId : String
    private lateinit var orderSummaryAdapter : OrderSummaryAdapter
    private val listOrder = mutableListOf<ListMenu>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCanteenOrderDetailsBinding.inflate(inflater, container, false)
        orderId = arguments?.getString("id").toString()
        Log.d(LOG, "orderID: $orderId")

        orderSummaryAdapter = OrderSummaryAdapter(listOrder)

        binding.orderDetailsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CanteenOrderDetailsFragment.context)
            adapter = orderSummaryAdapter
        }

        binding.detailOrderId.text = orderId

        getData.getSpecificOrderItem(orderId, this)

        return binding.root
    }

    override fun returnList(item: Collection<*>) {
        super.returnList(item)
        orderSummary.clear()
        orderSummary.addAll(item as Collection<ListOrder>)
        studentId = orderSummary.first().studentId

        val list = Gson().fromJson(orderSummary.first().order, Array<ListMenu>::class.java)
        listOrder.addAll(list)

        binding.orderStatus.text = orderSummary.first().status.uppercase(Locale.getDefault())

        binding.orderChangeStatusBtn.isEnabled = !orderSummary.first().status.contains("ready to pickup") && !orderSummary.first().status.contains("canceled")

        Log.d(LOG, "returnList: $orderSummary")
        orderSummaryAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.orderChangeStatusBtn.setOnClickListener {
            updateData.updateOrderStatus(orderId, studentId, this)
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.logoutBtn.setOnClickListener {
            Common().logout(requireActivity())

        }
    }

    override fun orderStatusUpdated(orderId: String, status: String) {
        super.orderStatusUpdated(orderId, status)
        if (status.isNotEmpty()) {
            binding.orderStatus.text = status.uppercase(Locale.getDefault())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}