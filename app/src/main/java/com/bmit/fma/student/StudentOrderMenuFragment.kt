package com.bmit.fma.student

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentStudentOrderMenuBinding
import com.bmit.fma.dialogs.AlertDialogCustom
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.interfaceListener.InterfaceListener
import com.bmit.fma.interfaceListener.ItemCallback
import com.google.android.material.snackbar.Snackbar

class StudentOrderMenuFragment : Fragment(), InterfaceListener, ItemCallback {

    private var _binding: FragmentStudentOrderMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var alertDialog: AlertDialogCustom
    private lateinit var sessionViewModel: SessionViewModel
    private lateinit var foodListAdapter: MenuListAdapter
    private lateinit var drinkListAdapter: MenuListAdapter
    private lateinit var snackListAdapter: MenuListAdapter
    private val foodMenu = mutableListOf<ListMenu>()
    private val drinkMenu = mutableListOf<ListMenu>()
    private val snackMenu = mutableListOf<ListMenu>()
    private val getData = GetData()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentOrderMenuBinding.inflate(inflater, container, false)
        sessionViewModel = ViewModelProvider(requireActivity())[SessionViewModel::class.java]
        foodListAdapter = MenuListAdapter(foodMenu, this@StudentOrderMenuFragment)
        drinkListAdapter = MenuListAdapter(drinkMenu, this@StudentOrderMenuFragment)
        snackListAdapter = MenuListAdapter(snackMenu, this@StudentOrderMenuFragment)

        alertDialog = AlertDialogCustom(requireActivity(), sessionViewModel)

        binding.foodCornerRecyclerView.apply {
            layoutManager = GridLayoutManager(this@StudentOrderMenuFragment.context, 2)
            adapter = foodListAdapter
        }
        binding.drinkCornerRecyclerView.apply {
            layoutManager = GridLayoutManager(this@StudentOrderMenuFragment.context, 2)
            adapter = drinkListAdapter
        }
        binding.snackCornerRecyclerView.apply {
            layoutManager = GridLayoutManager(this@StudentOrderMenuFragment.context, 2)
            adapter = snackListAdapter
        }

        getData.getMenu(this, sessionViewModel.getItemOrder())

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG, "OnStop")
    }

    override fun onItemUpdated() {
        super.onItemUpdated()
        getData.getMenu(this, sessionViewModel.getItemOrder())
    }

    override fun returnMenu(
        foodList: Collection<ListMenu>,
        drinkList: Collection<ListMenu>,
        snackList: Collection<ListMenu>
    ) {
        super.returnMenu(foodList, drinkList, snackList)
        foodMenu.clear()
        drinkMenu.clear()
        snackMenu.clear()

        foodMenu.addAll(foodList)
        drinkMenu.addAll(drinkList)
        snackMenu.addAll(snackList)

        foodListAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            if (!sessionViewModel.isOrderEmpty()) {
                findNavController().navigate(R.id.action_studentOrderMenuFragment_to_studentOrderReviewFragment)
            } else {
                Snackbar.make(requireView(), "Please place an order", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(itemId: String, itemBox: ConstraintLayout, listMenu: ListMenu?) {
        Toast.makeText(requireContext(), "click: $itemId", Toast.LENGTH_SHORT).show()
        if (listMenu != null) {
            alertDialog.showQuantitySelectionDialog(
                itemId = itemId,
                itemBox = itemBox,
                listMenu = listMenu,
                this
            )
        }
    }

}