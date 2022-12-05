package com.bmit.fma.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bmit.fma.databinding.FragmentStudentOrderTrackingBinding

class StudentOrderTrackingFragment: Fragment() {

    private var _binding : FragmentStudentOrderTrackingBinding? = null
    private val binding get() = _binding!!
    lateinit var orderID : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentOrderTrackingBinding.inflate(inflater, container, false)
        orderID = arguments?.getString("id").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.trackingOrderId.text = orderID
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}