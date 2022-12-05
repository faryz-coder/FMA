package com.bmit.fma.student

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentStudentOrderHistoryBinding
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.firebaseUtils.UpdateData
import com.bmit.fma.interfaceListener.InterfaceListener
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.viewmodel.LoginViewModel

class StudentOrderHistoryFragment: Fragment(), InterfaceListener, ItemCallback {
    private var _binding: FragmentStudentOrderHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionViewModel: SessionViewModel
    private lateinit var loginViewModel: LoginViewModel

    private val listHistory = mutableListOf<ListOrder>()
    private val getData = GetData()
    private val updateData = UpdateData()
    private lateinit var historyItemAdapter: HistoryItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentOrderHistoryBinding.inflate(inflater, container, false)
        sessionViewModel = ViewModelProvider(requireActivity())[SessionViewModel::class.java]
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        historyItemAdapter = HistoryItemAdapter(listHistory, this@StudentOrderHistoryFragment)

        binding.viewOrderRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@StudentOrderHistoryFragment.context)
            adapter = historyItemAdapter
        }

        getData.getOrderedHistory(this, loginViewModel.studentId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                        updateData.removeOrder(itemId, loginViewModel.studentId, this@StudentOrderHistoryFragment)
                    })
                setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }

            // Create the AlertDialog
            builder.create()
        }

        alertDialog!!.show()

    }

    override fun onItemClick(itemId: String, itemBox: ConstraintLayout, listMenu: ListMenu?) {
        super.onItemClick(itemId, itemBox, listMenu)
        val bundle = bundleOf("id" to itemId)
        findNavController().navigate(R.id.action_studentOrderHistoryFragment_to_studentOrderTrackingFragment, bundle)
    }

    override fun itemRemoved(itemID: String) {
        super.itemRemoved(itemID)
        listHistory.removeIf { it.orderId == itemID }
        historyItemAdapter.notifyDataSetChanged()
    }

    override fun returnList(item: Collection<*>) {
        super.returnList(item)
        listHistory.clear()
        listHistory.addAll(item as Collection<ListOrder>)
        historyItemAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}