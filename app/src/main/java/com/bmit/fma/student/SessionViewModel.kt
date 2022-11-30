package com.bmit.fma.student

import androidx.lifecycle.ViewModel
import com.bmit.fma.dialogs.ItemOrder

class SessionViewModel : ViewModel() {
    private val itemOrder = mutableListOf<ItemOrder>()

    fun addItem(itemId: String, quantity: String) {
        itemOrder.removeIf { it.itemId == itemId }
        itemOrder.add(ItemOrder(itemId, quantity))
    }

    fun getItemOrder(): MutableList<ItemOrder> {
        return itemOrder
    }

    fun removeItem(itemId: String) {
        itemOrder.removeIf { it.itemId == itemId }
    }

    fun clear() {
        itemOrder.clear()
    }
}