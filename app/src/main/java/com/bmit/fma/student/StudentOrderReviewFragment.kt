package com.bmit.fma.student

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmit.fma.R
import com.bmit.fma.databinding.FragmentStudentOrderReviewBinding

class StudentOrderReviewFragment: Fragment() {

    private var _binding: FragmentStudentOrderReviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionViewModel: SessionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val orderList = mutableListOf<ListMenu>()
        _binding = FragmentStudentOrderReviewBinding.inflate(inflater, container, false)
        sessionViewModel = ViewModelProvider(requireActivity())[SessionViewModel::class.java]
        d("StudentViewOrderFragment", "${sessionViewModel.getItemOrder()}" )

        orderList.add(
            ListMenu(
                imageURL = "https://rasamalaysia.com/wp-content/uploads/2007/01/nasi_lemak-1.jpg",
                name = "Nasi Lemak",
                price = "20.0",
                calories = "10",
                status = "true",
                type = "food",
                itemId = "1",
                quantity = "2"
            )
        )
        orderList.add(
            ListMenu(
                imageURL = "https://resepichenom.com/media/92631CD8-98DF-48A3-A396-46020FD88812.jpeg",
                name = "Nasi Goreng Cina",
                price = "20.0",
                calories = "10",
                status = "true",
                type = "food",
                itemId = "2",
                quantity = "1"
            )
        )

        binding.listOrderRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@StudentOrderReviewFragment.context)
            adapter = OrderListAdapter(orderList, this@StudentOrderReviewFragment)
        }

        var totalCal = 0
        var totalPrice = 0.00

        orderList.forEach {
            totalCal += it.calories.toInt() * it.quantity.toInt()
            totalPrice += it.price.toDouble() * it.quantity.toInt()
        }

        binding.totalCalories.text = getString(R.string.total_calories_intake) + totalCal.toString() + " Cal"
        binding.totalPrice.text = getString(R.string.total) + totalPrice

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