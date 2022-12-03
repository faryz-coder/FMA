package com.bmit.fma.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentStudentMenuBinding
import com.bmit.fma.viewmodel.LoginViewModel

class StudentMenuFragment: Fragment(), View.OnClickListener {

    private var _binding: FragmentStudentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentMenuBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewOrderBtn.setOnClickListener(this)
        binding.placeOrderBtn.setOnClickListener(this)
        binding.historyBtn.setOnClickListener(this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.placeOrderBtn.id -> {findNavController().navigate(R.id.action_studentMenuFragment_to_studentOrderMenuFragment)}
            binding.viewOrderBtn.id -> {findNavController().navigate(R.id.action_studentMenuFragment_to_studentOrderReviewFragment)}
            binding.historyBtn.id -> {findNavController().navigate(R.id.action_studentMenuFragment_to_studentOrderHistoryFragment)}
        }
    }
}