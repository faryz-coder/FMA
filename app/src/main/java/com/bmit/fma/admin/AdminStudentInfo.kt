package com.bmit.fma.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bmit.fma.FixNotation
import com.bmit.fma.databinding.FragmentAdminStudentInfoBinding
import com.bmit.fma.firebaseUtils.UpdateData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminStudentInfo : Fragment() {
    private var _binding : FragmentAdminStudentInfoBinding? = null

    private val binding get() = _binding!!
    private val db = Firebase.firestore
    lateinit var tempDoc: DocumentSnapshot
    lateinit var id: String
    private val updateData = UpdateData()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminStudentInfoBinding.inflate(inflater, container, false)

        id = arguments?.getString("id").toString()
        val doc = db.collection("Login").document(id)
        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    binding.inputStudentId.editText?.setText(document.get("studentId").toString())
                    binding.inputStudentName.editText?.setText(document.get("name").toString())
                    binding.inputStudentPassword.editText?.setText(document.get("password").toString())
                    tempDoc = document
                } else {
                    //no document
                    Log.d(FixNotation.LOG, "No info: $id")
                }
            }
            .addOnFailureListener {
                Log.d(FixNotation.LOG, "Failed to retrieve info: $it")
            }

        return binding.root
    }
}