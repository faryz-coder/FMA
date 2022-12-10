package com.bmit.fma.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bmit.fma.databinding.FragmentAdminAddStudentBinding
import com.bmit.fma.firebaseUtils.RegisterUser
import com.bmit.fma.utils.Common

class AdminAddStudentFragment : Fragment() {

    private var _binding: FragmentAdminAddStudentBinding? = null

    private val binding get() = _binding!!
    private lateinit var registerUser : RegisterUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminAddStudentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerUser = RegisterUser(requireView())

        binding.submitStudentBtn.setOnClickListener {
            registerUser.registerStudent(
                binding.inputStudentId.editText?.text.toString(),
                binding.inputStudentName.editText?.text.toString(),
                binding.inputStudentPassword.editText?.text.toString()
            )
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.logoutBtn.setOnClickListener {
            Common().logout(requireActivity())

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}