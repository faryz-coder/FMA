package com.bmit.fma.student

import androidx.constraintlayout.widget.ConstraintLayout

interface InterfaceListener {
    /**
     * Use when user click on item in the menu
     */
    fun onItemClick(itemId: String, itemBox: ConstraintLayout) {}

    fun onItemRemove(itemId: String){}
}