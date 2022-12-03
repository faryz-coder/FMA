package com.bmit.fma.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bmit.fma.R

class UserListAdapter(private val listStaff: MutableList<UserList>, var userListener: UserListener) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.name)
        val email : TextView = itemView.findViewById(R.id.email)
        val box : ConstraintLayout = itemView.findViewById(R.id.userBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_user_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = listStaff[position]
        holder.name.text = list.name
        holder.email.text = list.email

        holder.box.setOnClickListener {
            userListener.selectUser(list.id)
        }
    }

    override fun getItemCount(): Int {
        return listStaff.size
    }

}
