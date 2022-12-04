package com.bmit.fma.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
                    binding.inputAddress.editText?.setText(document.get("address").toString())
                    binding.inputAddress.editText?.setText(document.get("address").toString())
                    binding.inputStaffEmail.editText?.setText(document.get("email").toString())
                    binding.inputHandler.editText?.setText(document.get("handler").toString())
                    binding.inputStaffName.editText?.setText(document.get("name").toString())
                    binding.inputPhoneNumber.editText?.setText(document.get("phoneNo").toString())
                    binding.inputStaffPassword.editText?.setText(document.get("password").toString())
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
                binding.inputAddress.editText?.text.toString(),
                binding.inputHandler.editText?.text.toString(),
                binding.inputStaffName.editText?.text.toString(),
                binding.inputPhoneNumber.editText?.text.toString()
            )
        }

        binding.backBtn2.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}