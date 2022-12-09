package com.bmit.fma.Login

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bmit.fma.*
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.databinding.FragmentLoginUserBinding
import com.bmit.fma.firebaseUtils.LoginUser
import com.bmit.fma.interfaceListener.LoginCallback
import com.google.android.material.snackbar.Snackbar

class LoginUserFragment: Fragment(), LoginCallback {

    private var _binding: FragmentLoginUserBinding? = null

    private val binding get() = _binding!!
    lateinit var type: String
    private val loginUser = LoginUser()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginUserBinding.inflate(inflater, container, false)
        type = arguments?.getString("type").toString()

        when (type) {
            "student" -> {
                binding.IdTitle.text = "STUDENT ID"
                binding.loginBackground.setBackgroundResource(R.drawable.student_login)
            }
            "canteen" -> {
                binding.IdTitle.text = "STAFF ID"
                binding.loginBackground.setBackgroundResource(R.drawable.canteen_staff)
            }
            "admin" -> {
                binding.IdTitle.text = "ADMIN ID"
                binding.loginBackground.setBackgroundResource(R.drawable.admin_page)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            if (checkLogin()) {

                val emailorid = binding.inputEmail.editText?.text.toString()
                val password = binding.inputId.editText?.text.toString()
                when (type) {
                    "student" -> {
                        loginUser.studentLogin( emailorid,password, this )
                    }
                    "canteen" -> {
                        loginUser.canteenLogin( emailorid,password, this )
                    }
                    "admin" -> {
                        loginUser.adminLogin( emailorid,password, this )
                    }
                }
            }
        }
    }

    override fun isStudent(status: Boolean, studentId: String, type: String) {
        super.isStudent(status, studentId, type)
        if (!status){
            Snackbar.make(requireView(), "Login Failed", Snackbar.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(requireContext(), StudentActivity::class.java)
        intent.putExtra("id", studentId)
        intent.putExtra("type", type)

        try {
            startActivity(intent)
            activity?.finish()
        } catch (e: ActivityNotFoundException) {
            Log.e(LOG, e.toString())
        }
    }

    override fun isCanteen(status: Boolean, staffEmail: String, type: String) {
        super.isCanteen(status, staffEmail, type)
        if (!status){
            Snackbar.make(requireView(), "Login Failed", Snackbar.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(requireContext(), CanteenActivity::class.java)
        intent.putExtra("id", staffEmail)
        intent.putExtra("type", type)

        try {
            startActivity(intent)
            activity?.finish()
        } catch (e: ActivityNotFoundException) {
            Log.e(LOG, e.toString())
        }
    }

    override fun isAdmin(status: Boolean, adminEmail: String, type: String) {
        super.isAdmin(status, adminEmail, type)
        if (!status){
            Snackbar.make(requireView(), "Login Failed", Snackbar.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(requireContext(), AdminActivity::class.java)
        intent.putExtra("id", adminEmail)
        intent.putExtra("type", type)

        try {
            startActivity(intent)
            activity?.finish()
        } catch (e: ActivityNotFoundException) {
            Log.e(LOG, e.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkLogin() : Boolean{
        return (!binding.inputId.editText?.text.toString().isNullOrEmpty() &&
                !binding.inputEmail.editText?.text.toString().isNullOrEmpty())
    }
}