package com.bmit.fma.canteen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentCanteenMenuBinding
import com.bmit.fma.firebaseUtils.NotificationUtil
import com.bmit.fma.viewmodel.LoginViewModel

class CanteenMenuFragment: Fragment() {
    private var _binding : FragmentCanteenMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCanteenMenuBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addItemBtn.setOnClickListener {
            findNavController().navigate(R.id.action_canteenMenuFragment_to_canteenAddItemFragment)
        }
        binding.viewItemBtn.setOnClickListener {
            findNavController().navigate(R.id.action_canteenMenuFragment_to_canteenViewItemFragment)
        }
        binding.viewNewOrderBtn.setOnClickListener {
            findNavController().navigate(R.id.action_canteenMenuFragment_to_canteenOrderFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}