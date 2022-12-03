package com.bmit.fma.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentLoginSelectionBinding

class LoginSelectionFragment: Fragment() {

    private var _binding: FragmentLoginSelectionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdmin.setOnClickListener {
            val bundle = bundleOf("type" to "admin")
            findNavController().navigate(R.id.action_loginSelectionFragment_to_loginUserFragment, bundle)
        }
        binding.btnCanteen.setOnClickListener {
            val bundle = bundleOf("type" to "canteen")
            findNavController().navigate(R.id.action_loginSelectionFragment_to_loginUserFragment, bundle)
        }
        binding.btnStudent.setOnClickListener {
            val bundle = bundleOf("type" to "student")
            findNavController().navigate(R.id.action_loginSelectionFragment_to_loginUserFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}