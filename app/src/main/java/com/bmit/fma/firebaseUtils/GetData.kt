package com.bmit.fma.firebaseUtils

import android.util.Log
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.canteen.ItemCallback
import com.bmit.fma.canteen.ItemList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.Query.Direction

class GetData {

    private val db = Firebase.firestore

    fun itemList(callback: ItemCallback) {

        val listItem = mutableListOf<ItemList>()

        val doc = db.collection("canteen").document("menu").collection("list").orderBy("type", Direction.DESCENDING)
            doc.get()
                .addOnSuccessListener { document ->
                    document.forEach { item ->
                        listItem.add(
                            ItemList(
                                item["name"].toString(),
                                item["imageUrl"].toString(),
                                item["calories"].toString(),
                                item["price"].toString(),
                                item["status"].toString(),
                                item["type"].toString(),
                                item.id
                            )
                        )

                    }
                    callback.returnList(listItem)
                }
                .addOnFailureListener {
                    Log.d(LOG, "Failed to retrieve item")
                }
    }

    fun getItemInfo(itemId: String, callback: ItemCallback) {
        val doc = db.collection("canteen").document("menu").collection("list").document(itemId)
        doc.get()
            .addOnSuccessListener { item ->
                callback.onItemInfo(item)
                Log.d(LOG, "info retrieved")
            }
    }
}