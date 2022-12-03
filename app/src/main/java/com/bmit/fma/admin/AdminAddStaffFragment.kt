package com.bmit.fma.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.FixNotation.STAFF
import com.bmit.fma.databinding.FragmentAdminAddStaffBinding
import com.bmit.fma.firebaseUtils.RegisterUser
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
                binding.inputStaffEmail.text.toString(),
                binding.inputStaffName.text.toString(),
                binding.inputHandler.text.toString(),
                binding.inputAddress.text.toString(),
                binding.inputPhoneNumber.text.toString(),
                binding.inputStaffPassword.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}