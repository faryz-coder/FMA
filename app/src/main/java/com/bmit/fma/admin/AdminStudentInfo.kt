package com.bmit.fma.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bmit.fma.canteen.ItemCallback
import com.bmit.fma.databinding.FragmentAdminStudentInfoBinding
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.firebaseUtils.UpdateData
import com.bmit.fma.utils.Validation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminStudentInfo : Fragment(), ItemCallback {
    private var _binding: FragmentAdminStudentInfoBinding? = null

    private val binding get() = _binding!!
    private val db = Firebase.firestore
    lateinit var tempDoc: DocumentSnapshot
    lateinit var itemId: String
    private val updateData = UpdateData()
    private val validate = Validation()
    private val getData = GetData()
    lateinit var studentInfo: DocumentSnapshot

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminStudentInfoBinding.inflate(inflater, container, false)

        itemId = arguments?.getString("id").toString()

        getData.getStudentInfo(itemId, this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUpdate.setOnClickListener {
            if (validate.validateToUpdateStudent(
                    studentInfo,
                    itemId,
                    binding.inputStudentId.editText?.text.toString(),
                    binding.inputStudentName.editText?.text.toString(),
                    binding.inputStudentPassword.editText?.text.toString(),
                )
            ) {
                updateData.updateStudentInfo(
                    itemId,
                    binding.inputStudentId.editText?.text.toString(),
                    binding.inputStudentName.editText?.text.toString(),
                    binding.inputStudentPassword.editText?.text.toString(),
                    this
                )
            } else {
                Snackbar.make(requireView(), "Same data", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        binding.backBtn3.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onItemInfo(item: DocumentSnapshot) {
        super.onItemInfo(item)
        studentInfo = item
        binding.inputStudentId.editText?.setText(item.get("studentId").toString())
        binding.inputStudentName.editText?.setText(item.get("name").toString())
        binding.inputStudentPassword.editText?.setText(item.get("password").toString())

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