package com.bmit.fma.canteen

import com.google.firebase.firestore.DocumentSnapshot

interface ItemCallback {

    /**
     * return with list of item in the db
     */
    fun returnList(item: MutableList<ItemList>) {}

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

    fun onItemUpdated() {

    }
}