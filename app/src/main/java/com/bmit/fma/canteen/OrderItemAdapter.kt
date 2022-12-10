package com.bmit.fma.canteen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmit.fma.R
import com.bmit.fma.interfaceListener.InterfaceListener
import com.bmit.fma.student.ListMenu
import com.bmit.fma.student.ListOrder
import com.bmit.fma.student.OrderSummaryAdapter
import com.google.gson.Gson

class OrderItemAdapter(private val listOrder: MutableList<ListOrder>, private val onClickInterface: InterfaceListener) : RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val date : TextView = itemView.findViewById(R.id.historyDate)
        val deleteBtn : Button = itemView.findViewById(R.id.deleteOrderBtn)
        val orderSummaryRecyclerView : RecyclerView = itemView.findViewById(R.id.orderSummaryList)
        val status : TextView = itemView.findViewById(R.id.order_status)
        val myContext: Context = itemView.context
        val itemBox : ConstraintLayout = itemView.findViewById(R.id.itemBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_order_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = listOrder[position]
        holder.date.text = "Date: "+ history.date
        holder.status.text = history.status
        holder.deleteBtn.isVisible = false

        val listOrder = mutableListOf<ListMenu>()
        listOrder.addAll(Gson().fromJson(history.order, Array<ListMenu>::class.java))

        val orderSummaryAdapter = OrderSummaryAdapter(listOrder)
        holder.orderSummaryRecyclerView.apply {
            layoutManager = LinearLayoutManager(holder.myContext)
            adapter = orderSummaryAdapter
        }

        holder.itemBox.setOnClickListener {
            onClickInterface.onSelectOrder(history.orderId)
        }
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

}
