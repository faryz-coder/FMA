package com.bmit.fma.interfaceListener

import androidx.constraintlayout.widget.ConstraintLayout

interface InterfaceListener {
    /**
     * Use when user click on item in the menu
     */
    fun onItemClick(itemId: String, itemBox: ConstraintLayout) {}

    /**
     * When user click delete button
     */
    fun onClickDelete(itemId: String){}
}