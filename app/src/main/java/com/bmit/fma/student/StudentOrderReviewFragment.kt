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
import com.bmit.fma.firebaseUtils.GetData
import com.bmit.fma.firebaseUtils.UpdateData
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class StudentOrderReviewFragment: Fragment(), ItemCallback {

    private var _binding: FragmentStudentOrderReviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionViewModel: SessionViewModel
    private lateinit var loginViewModel: LoginViewModel
    private val getData = GetData()
    private val updateData = UpdateData()
    private val orderList = mutableListOf<ListMenu>()
    lateinit var orderListAdapter: OrderListAdapter
    private var total: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentOrderReviewBinding.inflate(inflater, container, false)
        sessionViewModel = ViewModelProvider(requireActivity())[SessionViewModel::class.java]
        orderListAdapter = OrderListAdapter(orderList, this@StudentOrderReviewFragment)
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        d("StudentViewOrderFragment", "${sessionViewModel.getItemOrder()}" )

        binding.listOrderRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@StudentOrderReviewFragment.context)
            adapter = orderListAdapter
        }

        getData.getOrderedItem(sessionViewModel.getItemOrder(), this)
        return binding.root
    }

    override fun returnOrderItemList(item: Collection<*>, totalPrice: Double, totalCal: Int) {
        super.returnOrderItemList(item, totalPrice, totalCal)
        orderList.clear()
        orderList.addAll(item as Collection<ListMenu>)

        orderListAdapter.notifyDataSetChanged()
        binding.totalCalories.text = getString(R.string.total_calories_intake) + totalCal.toString() + " Cal"
        binding.totalPrice.text = getString(R.string.total) + totalPrice
        total = totalPrice

        binding.btnPay.isEnabled = item.isNotEmpty()
    }

    override fun onItemUpdated() {
        super.onItemUpdated()
        Snackbar.make(requireView(), "Payment Confirmed", Snackbar.LENGTH_SHORT).show()
        sessionViewModel.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPay.setOnClickListener {

            updateData.confirmPayment(orderList, total, loginViewModel.studentId, this)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}