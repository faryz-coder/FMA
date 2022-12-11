package com.bmit.fma.interfaceListener

import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bmit.fma.student.ListMenu
import com.google.android.material.card.MaterialCardView

interface InterfaceListener {
    /**
     * Use when user click on item in the menu
     */
    fun onItemClick(itemId: String, itemBox: MaterialCardView? =null, listMenu: ListMenu? = null) {}

    /**
     * When user click delete button
     */
    fun onClickDelete(itemId: String){}

    fun onSelectOrder(itemId: String) {}
}