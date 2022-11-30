package com.bmit.fma.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentStudentOrderMenuBinding
import com.bmit.fma.dialogs.AlertDialogCustom

class StudentOrderMenuFragment: Fragment(), InterfaceListener {

    private var _binding: FragmentStudentOrderMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var alertDialog : AlertDialogCustom
    private  lateinit var sessionViewModel: SessionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentOrderMenuBinding.inflate(inflater, container, false)
        sessionViewModel = ViewModelProvider(requireActivity())[SessionViewModel::class.java]

        alertDialog = AlertDialogCustom(requireActivity(), sessionViewModel)
        val menuList = mutableListOf<ListMenu>()

        menuList.add(
            ListMenu(
                imageURL = "https://rasamalaysia.com/wp-content/uploads/2007/01/nasi_lemak-1.jpg",
                name = "Nasi Lemak",
                price = "20.0",
                calories = "10",
                status = "true",
                type = "food",
                itemId = "1"
            )
        )
        menuList.add(
            ListMenu(
                imageURL = "https://resepichenom.com/media/92631CD8-98DF-48A3-A396-46020FD88812.jpeg",
                name = "Nasi Goreng Cina",
                price = "20.0",
                calories = "10",
                status = "true",
                type = "food",
                itemId = "2"
            )
        )

        binding.foodCornerRecyclerView.apply {
            layoutManager = GridLayoutManager(this@StudentOrderMenuFragment.context, 2)
            adapter = MenuListAdapter(menuList, this@StudentOrderMenuFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_studentOrderMenuFragment_to_studentOrderReviewFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(itemId: String, itemBox: ConstraintLayout) {
        Toast.makeText(requireContext(), "click: $itemId", Toast.LENGTH_SHORT).show()
        alertDialog.showQuantitySelectionDialog(
            itemId = itemId,
            itemBox = itemBox
        )
    }

}