package com.bmit.fma.interfaceListener

import com.bmit.fma.student.ListMenu
import com.google.firebase.firestore.DocumentSnapshot

interface ItemCallback {

    /**
     * return with list of item in the db
     */
    fun returnList(item: Collection<*>) {}

    /**
     * notified that item successfully removed
     */

    fun itemRemoved(itemID: String) {

    }

    /**
     *  called when item failed to removed from db
     */
    fun removedFailed() {

    }


    fun onItemInfo(item: DocumentSnapshot) {

    }

    /**
     * used when item successfully updated
     */
    fun onItemUpdated() {

    }

    fun returnMenu(foodList: Collection<ListMenu>, drinkList: Collection<ListMenu>, snackList: Collection<ListMenu>) {

    }

    fun returnOrderItemList(item: Collection<*>, totalPrice: Double, totalCal: Int) {

    }
}