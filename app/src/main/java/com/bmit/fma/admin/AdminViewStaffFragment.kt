package com.bmit.fma.admin

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmit.fma.R
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.databinding.FragmentAdminViewStaffBinding
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.firebaseUtils.UpdateData
import com.bmit.fma.interfaceListener.InterfaceListener
import com.bmit.fma.student.ListMenu
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminViewStaffFragment : Fragment(), ItemCallback, InterfaceListener {

    private var _binding: FragmentAdminViewStaffBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    private val listStaff = mutableListOf<UserList>()
    private val updateData = UpdateData()
    private lateinit var userListAdapter : UserListAdapter
    private val getData = GetData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminViewStaffBinding.inflate(inflater, container, false)
        userListAdapter = UserListAdapter(listStaff, this@AdminViewStaffFragment)

        binding.listStaffRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@AdminViewStaffFragment.context)
            adapter = userListAdapter
        }

        getData.staffList(this)
        return binding.root
    }

    override fun returnList(item: Collection<*>) {
        super.returnList(item)
        listStaff.clear()
        listStaff.addAll(item as Collection<UserList>)
        userListAdapter.notifyDataSetChanged()

    }

    override fun itemRemoved(itemID: String) {
        super.itemRemoved(itemID)
        listStaff.removeIf { it.id == itemID }
        userListAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn4.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(itemId: String, itemBox: ConstraintLayout, listMenu: ListMenu?) {
        val bundle = bundleOf("id" to itemId)
        findNavController().navigate(R.id.action_adminViewStaffFragment_to_adminStaffInfo, bundle)
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
                        updateData.removeUser(itemId, this@AdminViewStaffFragment)
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
}
