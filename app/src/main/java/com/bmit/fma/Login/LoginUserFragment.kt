package com.bmit.fma.Login

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.MainActivity
import com.bmit.fma.databinding.FragmentLoginUserBinding

class LoginUserFragment: Fragment() {

    private var _binding: FragmentLoginUserBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {

            if (checkLogin()) {
                saveLoginInfo()
                openAdmin()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkLogin() : Boolean{
        return true
    }

    private fun openAdmin() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        try {
            startActivity(intent)
            activity?.finish()
        } catch (e: ActivityNotFoundException) {
            Log.e(LOG, e.toString())
        }
    }

    private fun saveLoginInfo() {

    }
}