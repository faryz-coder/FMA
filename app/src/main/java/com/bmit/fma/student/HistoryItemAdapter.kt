package com.bmit.fma.student

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmit.fma.R
import com.bmit.fma.interfaceListener.InterfaceListener
import com.bmit.fma.interfaceListener.ItemCallback
import com.google.gson.Gson
import kotlinx.coroutines.*

class HistoryItemAdapter(private val listHistory: MutableList<ListOrder>, val onClickInterface: InterfaceListener) : RecyclerView.Adapter<HistoryItemAdapter.ViewHolder>(), ItemCallback {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
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
        val history = listHistory[position]
        holder.date.text = "Date: "+ history.date
        holder.status.text = history.status

        val listOrder = mutableListOf<ListMenu>()
        listOrder.addAll(Gson().fromJson(history.order, Array<ListMenu>::class.java))
        val orderSummaryAdapter = OrderSummaryAdapter(listOrder)
            holder.orderSummaryRecyclerView.apply {
                layoutManager = LinearLayoutManager(holder.myContext)
                adapter = orderSummaryAdapter
            }
        holder.deleteBtn.setOnClickListener {
            onClickInterface.onClickDelete(history.orderId)
        }

        holder.itemBox.setOnClickListener {
            onClickInterface.onItemClick(history.orderId, null)
        }
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

}
