package com.bmit.fma.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.databinding.FragmentAdminStaffInfoBinding
import com.bmit.fma.firebaseUtils.UpdateData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase

class AdminStaffInfo : Fragment() {
    private var _binding: FragmentAdminStaffInfoBinding? = null

    private val binding get() = _binding!!
    private val db = Firebase.firestore
    lateinit var tempDoc: DocumentSnapshot
    lateinit var id: String
    private val updateData = UpdateData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminStaffInfoBinding.inflate(inflater, container, false)

        id = arguments?.getString("id").toString()
        val doc = db.collection("Login").document(id.toString())
        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    binding.inputAddress.setText(document.get("address").toString())
                    binding.inputStaffEmail.setText(document.get("email").toString())
                    binding.inputHandler.setText(document.get("handler").toString())
                    binding.inputStaffName.setText(document.get("name").toString())
                    binding.inputPhoneNumber.setText(document.get("phoneNo").toString())
                    binding.inputStaffPassword.setText(document.get("password").toString())
                    tempDoc = document
                } else {
                    //no document
                    Log.d(LOG, "No info: $id")
                }
            }
            .addOnFailureListener {
                Log.d(LOG, "Failed to retrieve info: $it")
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Snackbar.make(requireView(), "id: $id", Snackbar.LENGTH_SHORT)
            .show()
        binding.btnUpdateStaff.setOnClickListener {
            updateData.updateStaffInfo(
                id,
                binding.inputAddress.text.toString(),
                binding.inputHandler.text.toString(),
                binding.inputStaffName.text.toString(),
                binding.inputPhoneNumber.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}