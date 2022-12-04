package com.bmit.fma.canteen

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentCanteenViewItemBinding
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.firebaseUtils.UpdateData
import com.bmit.fma.interfaceListener.InterfaceListener
import kotlinx.coroutines.NonCancellable.cancel

class CanteenViewItemFragment : Fragment(), ItemCallback, InterfaceListener {

    private var _binding : FragmentCanteenViewItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemAdapter : ListItemAdapter
    private val listItem = mutableListOf<ItemList>()
    private val getData = GetData()
    private val updateData = UpdateData()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCanteenViewItemBinding.inflate(inflater, container, false)

        itemAdapter = ListItemAdapter(listItem, this@CanteenViewItemFragment)

        binding.listItemRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CanteenViewItemFragment.context)
            adapter = itemAdapter
        }
        getData.itemList(this)

        return binding.root
    }

    override fun returnList(item: MutableList<ItemList>) {
        super.returnList(item)
        listItem.clear()
        listItem.addAll(item)
        itemAdapter.notifyDataSetChanged()
    }

    override fun itemRemoved(itemID: String) {
        super.itemRemoved(itemID)
        listItem.removeIf { it.id == itemID }
        itemAdapter.notifyDataSetChanged()
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
                        updateData.removeItemData(itemId, this@CanteenViewItemFragment)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn8.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onItemClick(itemId: String, itemBox: ConstraintLayout) {
        super.onItemClick(itemId, itemBox)
        val bundle = bundleOf("itemId" to itemId)
        findNavController().navigate(R.id.action_canteenViewItemFragment_to_canteenItemInfoFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}