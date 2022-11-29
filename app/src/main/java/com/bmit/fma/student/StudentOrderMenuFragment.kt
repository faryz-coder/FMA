package com.bmit.fma.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bmit.fma.databinding.FragmentStudentOrderMenuBinding

class StudentOrderMenuFragment: Fragment() {

    private var _binding: FragmentStudentOrderMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentOrderMenuBinding.inflate(inflater, container, false)
        val menuList = mutableListOf<ListMenu>()

        menuList.add(
            ListMenu(
                imageURL = "https://rasamalaysia.com/wp-content/uploads/2007/01/nasi_lemak-1.jpg",
                name = "Nasi Lemak",
                price = "20.0",
                calories = "10",
                status = "true",
                type = "food"
            )
        )
        menuList.add(
            ListMenu(
                imageURL = "https://resepichenom.com/media/92631CD8-98DF-48A3-A396-46020FD88812.jpeg",
                name = "Nasi Goreng Cina",
                price = "20.0",
                calories = "10",
                status = "true",
                type = "food"
            )
        )

        binding.foodCornerRecyclerView.apply {
            layoutManager = GridLayoutManager(this@StudentOrderMenuFragment.context, 3)
            adapter = MenuListAdapter(menuList)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}