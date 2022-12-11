package com.bmit.fma.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.FixNotation.STAFF
import com.bmit.fma.databinding.FragmentAdminAddStaffBinding
import com.bmit.fma.firebaseUtils.RegisterUser
import com.bmit.fma.utils.Common
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminAddStaffFragment : Fragment() {

    private var _binding: FragmentAdminAddStaffBinding? = null

    private val binding get() = _binding!!
    private lateinit var registerUser : RegisterUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminAddStaffBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerUser = RegisterUser(requireView())

        binding.submitStaffBtn.setOnClickListener {
            registerUser.registerStaff(
                binding.inputStaffEmail.editText?.text.toString(),
                binding.inputStaffName.editText?.text.toString(),
                binding.inputHandler.editText?.text.toString(),
                binding.inputAddress.editText?.text.toString(),
                binding.inputPhoneNumber.editText?.text.toString(),
                binding.inputStaffPassword.editText?.text.toString()
            )
        }

        binding.logoutBtn.setOnClickListener {
            Common().logout(requireActivity())

        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}