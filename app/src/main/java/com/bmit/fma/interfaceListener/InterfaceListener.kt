package com.bmit.fma.interfaceListener

import androidx.constraintlayout.widget.ConstraintLayout
import com.bmit.fma.student.ListMenu

interface InterfaceListener {
    /**
     * Use when user click on item in the menu
     */
    fun onItemClick(itemId: String, itemBox: ConstraintLayout, listMenu: ListMenu? = null) {}

    /**
     * When user click delete button
     */
    fun onClickDelete(itemId: String){}
}