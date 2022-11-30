package com.bmit.fma.student

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bmit.fma.R
import java.util.concurrent.Executors

class OrderListAdapter(private val orderList: MutableList<ListMenu>, studentOrderReviewFragment: StudentOrderReviewFragment) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    class ViewHolder (itemview: View): RecyclerView.ViewHolder(itemview){
        val orderNo : TextView = itemView.findViewById(R.id.orderNo)
        val orderName : TextView = itemview.findViewById(R.id.orderName)
        val orderPrice : TextView = itemview.findViewById(R.id.orderPrice)
        val orderQuantity : TextView = itemView.findViewById(R.id.orderQuantity)
        val orderTotalPrice: TextView = itemView.findViewById(R.id.orderTotalPrice)
        val orderCal : TextView = itemView.findViewById(R.id.orderCal)
        val orderImg : ImageView = itemview.findViewById(R.id.orderImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = orderList[position]
        holder.orderNo.text = (position + 1).toString()
        holder.orderName.text = list.name
        holder.orderCal.text = list.calories+" Cal"
        holder.orderPrice.text = list.price
        holder.orderQuantity.text = list.quantity
        holder.orderTotalPrice.text = "RM "+(list.price.toDouble() * list.quantity.toInt()).toString()

        val handler = Handler(Looper.getMainLooper())
        val executor = Executors.newSingleThreadExecutor()

        // Grab Image online and display
        executor.execute {
            try {
                val `in` = java.net.URL(list.imageURL).openStream()
                val img = BitmapFactory.decodeStream(`in`)

                handler.post{
                    holder.orderImg.setImageBitmap(img)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

}
