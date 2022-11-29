package com.bmit.fma.student

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bmit.fma.R
import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

class MenuListAdapter(private val menuList: MutableList<ListMenu>) : RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val foodName : TextView = itemView.findViewById(R.id.itemName)
        val price : TextView = itemView.findViewById(R.id.itemPrice)
        val itemImage : ImageView = itemView.findViewById(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_menu_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listMenu = menuList[position]
        holder.foodName.text = listMenu.name
        holder.price.text = listMenu.price

        val handler = Handler(Looper.getMainLooper())
        val executor = Executors.newSingleThreadExecutor()

        // Grab Image online and display
        executor.execute {
            try {
                val `in` = java.net.URL(listMenu.imageURL).openStream()
                val img = BitmapFactory.decodeStream(`in`)

                handler.post{
                    holder.itemImage.setImageBitmap(img)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

}
