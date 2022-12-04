package com.bmit.fma.canteen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmit.fma.databinding.FragmentCanteenViewItemBinding

class CanteenViewItemFragment : Fragment() {

    private var _binding : FragmentCanteenViewItemBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCanteenViewItemBinding.inflate(inflater, container, false)

        val listItem = mutableListOf<ItemList>()

        binding.listItemRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CanteenViewItemFragment.context)
            adapter = ListItemAdapter(listItem, this@CanteenViewItemFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}