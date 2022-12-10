package com.bmit.fma.student

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bmit.fma.databinding.FragmentStudentOrderTrackingBinding
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.utils.Common

class StudentOrderTrackingFragment: Fragment(), ItemCallback {

    private var _binding : FragmentStudentOrderTrackingBinding? = null
    private val binding get() = _binding!!
    lateinit var orderID : String
    private val getData = GetData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentOrderTrackingBinding.inflate(inflater, container, false)
        orderID = arguments?.getString("id").toString()
        getData.getSpecificOrderItem(orderID, this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.trackingOrderId.text = orderID

        binding.backBtn8.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.logoutBtn2.setOnClickListener {
            Common().logout(requireActivity())
        }
    }

    override fun returnList(item: Collection<*>) {
        super.returnList(item)
        val list = item as Collection<ListOrder>
        orderStatus(list.first().status)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun orderStatus(status: String) {

        when {
            status.contains("order confirmed") -> {
                binding.orderConfirmedImg.setColorFilter(Color.GREEN)
            }
            status.contains("order processed") -> {
                binding.orderConfirmedImg.setColorFilter(Color.GREEN)
                binding.orderProcessedImg.setColorFilter(Color.GREEN)
            }
            status.contains("ready to pickup") -> {
                binding.orderConfirmedImg.setColorFilter(Color.GREEN)
                binding.orderProcessedImg.setColorFilter(Color.GREEN)
                binding.orderReadyImg.setColorFilter(Color.GREEN)
            }
        }
    }
}