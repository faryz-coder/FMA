package com.bmit.fma.student

import androidx.lifecycle.ViewModel
import com.bmit.fma.dialogs.ItemOrder

class SessionViewModel : ViewModel() {
    private val itemOrder = mutableListOf<ItemOrder>()

    /**
     * Add order
     */
    fun addItem(itemId: String, quantity: String, listMenu: ListMenu) {
        itemOrder.removeIf { it.itemId == itemId }
        itemOrder.add(ItemOrder(
            itemId,
            listMenu.name,
            listMenu.imageURL,
            listMenu.price,
            listMenu.calories,
            listMenu.status,
            listMenu.type,
            quantity
        ))
    }

    /**
     * Return list of order
     */
    fun getItemOrder(): MutableList<ItemOrder> {
        return itemOrder
    }

    /**
     * Remove order item
     */
    fun removeItem(itemId: String) {
        itemOrder.removeIf { it.itemId == itemId }
    }

    /**
     * Clear order
     */
    fun clear() {
        itemOrder.clear()
    }

    /**
     * Check if order empty
     */
    fun isOrderEmpty(): Boolean {
        return itemOrder.isEmpty()
    }
}