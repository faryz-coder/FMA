package com.bmit.fma.admin

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmit.fma.R
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.databinding.FragmentAdminViewStudentBinding
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.firebaseUtils.UpdateData
import com.bmit.fma.interfaceListener.InterfaceListener
import com.bmit.fma.student.ListMenu
import com.bmit.fma.utils.Common
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminViewStudentFragment : Fragment(), InterfaceListener, ItemCallback {

    private var _binding : FragmentAdminViewStudentBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    lateinit var myAdapter: UserListAdapter
    private val listStudent = mutableListOf<UserList>()
    private val getData = GetData()
    private val updateData = UpdateData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminViewStudentBinding.inflate(inflater, container, false)

        myAdapter = UserListAdapter(listStudent, this@AdminViewStudentFragment)

        binding.listStudentRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@AdminViewStudentFragment.context)
            adapter = myAdapter
        }
        getData.studentList(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.logoutBtn.setOnClickListener {
            Common().logout(requireActivity())

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun returnList(item: Collection<*>) {
        super.returnList(item)
        listStudent.clear()
        listStudent.addAll(item as Collection<UserList>)
        myAdapter.notifyDataSetChanged()
    }

    override fun itemRemoved(itemID: String) {
        super.itemRemoved(itemID)
        listStudent.removeIf { it.id == itemID }
        myAdapter.notifyDataSetChanged()
    }

    override fun onClickDelete(itemId: String) {
        super.onClickDelete(itemId)
        // show confirmation
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.apply {
                setTitle("Are you confirm: $itemId")
                setPositiveButton(
                    R.string.confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                        updateData.removeUser(itemId, this@AdminViewStudentFragment)
                    })
                setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }

            // Create the AlertDialog
            builder.create()
        }

        alertDialog!!.show()

    }

    override fun onItemClick(itemId: String, itemBox: MaterialCardView?, listMenu: ListMenu?) {
        super.onItemClick(itemId, null, listMenu)
        val bundle = bundleOf("id" to itemId)
        findNavController().navigate(R.id.action_adminViewStudentFragment_to_adminStudentInfo, bundle)
    }
}
