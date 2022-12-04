package com.bmit.fma.canteen

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
}