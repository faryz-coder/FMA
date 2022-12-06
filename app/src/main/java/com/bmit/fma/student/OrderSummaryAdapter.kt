package com.bmit.fma.student

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bmit.fma.FixNotation
import com.bmit.fma.R

class OrderSummaryAdapter(private val listOrder: MutableList<ListMenu>) : RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.summaryName)
        val quantity: TextView = itemView.findViewById(R.id.summaryQuantity)
        val total: TextView = itemView.findViewById(R.id.summaryTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_summary_row, parent, false)
        Log.d(FixNotation.LOG, "listOrder: ${listOrder.size}")

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = listOrder[position]
        Log.d(FixNotation.LOG, "name: ${order.name}")

        holder.name.text = order.name
        holder.quantity.text = order.quantity+"x"
        holder.total.text = "RM "+order.price
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

}
