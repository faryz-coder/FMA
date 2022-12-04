package com.bmit.fma.canteen

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bmit.fma.R
import com.bmit.fma.interfaceListener.InterfaceListener
import java.util.concurrent.Executors

class ListItemAdapter(private val listItem: MutableList<ItemList>, private val onClickInterface: InterfaceListener) : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val no: TextView = itemView.findViewById(R.id.itemNo)
        val name: TextView = itemView.findViewById(R.id.itemName)
        val calories: TextView = itemView.findViewById(R.id.itemCal)
        val price: TextView = itemView.findViewById(R.id.itemPrice)
        val image: ImageView = itemView.findViewById(R.id.itemImage)
        val delete: Button = itemView.findViewById(R.id.btnDelete)
        val itemBox: ConstraintLayout = itemView.findViewById(R.id.itemBox)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        holder.name.text = item.name
        holder.no.text = (position +1).toString()
        holder.calories.text = item.calories
        holder.price.text = item.price

        val handler = Handler(Looper.getMainLooper())
        val executor = Executors.newSingleThreadExecutor()

        // Grab Image online and display
        executor.execute {
            try {
                val `in` = java.net.URL(item.imageURL).openStream()
                val img = BitmapFactory.decodeStream(`in`)

                handler.post{
                    holder.image.setImageBitmap(img)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        holder.delete.setOnClickListener {
            onClickInterface.onClickDelete(item.id)
        }

        holder.itemBox.setOnClickListener {
            onClickInterface.onItemClick(item.id, holder.itemBox)
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

}
