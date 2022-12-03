package com.bmit.fma.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentAdminMenuBinding

class AdminMenuFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAdminMenuBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAdminMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addStudentBtn.setOnClickListener(this)
        binding.addCanteenBtn.setOnClickListener(this)
        binding.viewStudentBtn.setOnClickListener(this)
        binding.viewCanteenBtn.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when(v.id){
            binding.addCanteenBtn.id -> {
                findNavController().navigate(R.id.action_adminMenuFragment_to_adminAddStaffFragment)
            }
            binding.addStudentBtn.id -> {
                findNavController().navigate(R.id.action_adminMenuFragment_to_adminAddStudentFragment)
            }
            binding.viewStudentBtn.id -> {
                findNavController().navigate(R.id.action_adminMenuFragment_to_adminViewStudentFragment)
            }
            binding.viewCanteenBtn.id -> {
                findNavController().navigate(R.id.action_adminMenuFragment_to_adminViewStaffFragment)
            }
        }
    }
}