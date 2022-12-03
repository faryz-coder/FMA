package com.bmit.fma.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bmit.fma.databinding.FragmentAdminAddStudentBinding
import com.bmit.fma.firebaseUtils.RegisterUser

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
                binding.inputStudentId.text.toString(),
                binding.inputStudentName.text.toString(),
                binding.inputStudentPassword.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}