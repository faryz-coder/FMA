package com.bmit.fma.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.databinding.FragmentAdminStaffInfoBinding
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.firebaseUtils.UpdateData
import com.bmit.fma.utils.Common
import com.bmit.fma.utils.Validation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminStaffInfo : Fragment(), ItemCallback {
    private var _binding: FragmentAdminStaffInfoBinding? = null

    private val binding get() = _binding!!
    private val db = Firebase.firestore
    lateinit var staffId: String
    private val updateData = UpdateData()
    private val validate = Validation()
    private val getData = GetData()
    lateinit var staffInfo: DocumentSnapshot

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminStaffInfoBinding.inflate(inflater, container, false)

        staffId = arguments?.getString("id").toString()

        getData.getStaffInfo(staffId, this)
        return binding.root
    }

    override fun onItemInfo(item: DocumentSnapshot) {
        super.onItemInfo(item)
        staffInfo = item
        binding.inputAddress.editText?.setText(item.get("address").toString())
        binding.inputAddress.editText?.setText(item.get("address").toString())
        binding.inputStaffEmail.editText?.setText(item.get("email").toString())
        binding.inputHandler.editText?.setText(item.get("handler").toString())
        binding.inputStaffName.editText?.setText(item.get("name").toString())
        binding.inputPhoneNumber.editText?.setText(item.get("phoneNo").toString())
        binding.inputStaffPassword.editText?.setText(item.get("password").toString())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnUpdateStaff.setOnClickListener {
            if (validate.validateToUpdateStaff(
                    staffInfo,
                    binding.inputStaffEmail.editText?.text.toString(),
                    binding.inputStaffName.editText?.text.toString(),
                    binding.inputHandler.editText?.text.toString(),
                    binding.inputAddress.editText?.text.toString(),
                    binding.inputPhoneNumber.editText?.text.toString(),
                    binding.inputStaffPassword.editText?.text.toString()
                )
            ) {
                updateData.updateStaffInfo(
                    staffId,
                    binding.inputAddress.editText?.text.toString(),
                    binding.inputHandler.editText?.text.toString(),
                    binding.inputStaffName.editText?.text.toString(),
                    binding.inputPhoneNumber.editText?.text.toString(),
                    this
                )
            } else {
                Snackbar.make(requireView(), "Same data", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.logoutBtn.setOnClickListener {
            Common().logout(requireActivity())

        }
    }

    override fun onItemUpdated() {
        super.onItemUpdated()
        Snackbar.make(requireView(), "Details updated", Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}