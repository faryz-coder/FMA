package com.bmit.fma.student

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentStudentMenuBinding
import com.bmit.fma.utils.Common
import com.bmit.fma.viewmodel.LoginViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.Duration.Companion.days

class StudentMenuFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentStudentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sessionViewModel: SessionViewModel
    private val selectDate = datePicker()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentMenuBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        sessionViewModel = ViewModelProvider(requireActivity())[SessionViewModel::class.java]


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewOrderBtn.setOnClickListener(this)
        binding.placeOrderBtn.setOnClickListener(this)
        binding.historyBtn.setOnClickListener(this)

        selectDate.addOnPositiveButtonClickListener {
            try {
                val formatter = SimpleDateFormat("dd-MM-YYYY")
                val date = formatter.format(Date(it))
                sessionViewModel.setDate(date)
                findNavController().navigate(R.id.action_studentMenuFragment_to_studentOrderMenuFragment)
            } catch (e: Exception) {
                Log.e(LOG, "set date failed")
            }
        }

        binding.logoutBtn.setOnClickListener {
            Common().logout(requireActivity())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.placeOrderBtn.id -> {
//                findNavController().navigate(R.id.action_studentMenuFragment_to_studentOrderMenuFragment)
                selectDate.show(this.childFragmentManager, "tag")
            }
            binding.viewOrderBtn.id -> {
                findNavController().navigate(R.id.action_studentMenuFragment_to_studentOrderReviewFragment)
            }
            binding.historyBtn.id -> {
                findNavController().navigate(R.id.action_studentMenuFragment_to_studentOrderHistoryFragment)
            }
        }
    }

    fun datePicker(): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()
    }
}