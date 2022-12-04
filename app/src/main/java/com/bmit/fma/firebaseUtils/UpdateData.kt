package com.bmit.fma.firebaseUtils

import android.util.Log
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.canteen.ItemCallback
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class UpdateData {
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    fun updateStaffInfo(
        id: String,
        address: String,
        staffHandler: String,
        name: String,
        phoneNo: String
    ) {
        val data = hashMapOf(
            "name" to name,
            "handler" to staffHandler,
            "phoneNo" to phoneNo,
            "address" to address
        )

        db.collection("Login").document(id)
            .set(data, SetOptions.merge())
    }

    fun removeItemData(itemId: String, callback: ItemCallback) {
        db.collection("canteen")
            .document("menu")
            .collection("list")
            .document(itemId)
            .delete()
            .addOnSuccessListener {
                // Item Deleted from firestore
                // delete the image stored
                val storageRef = storage.reference
                val locationRef = storageRef.child(itemId)

                locationRef.delete()
                    .addOnSuccessListener {
                        callback.itemRemoved(itemId)
                        Log.d(LOG, "Item and Image Deleted")
                    }
                    .addOnFailureListener {
                        Log.d(LOG, "Failed to delete image")
                    }
            }
            .addOnFailureListener {
                callback.removedFailed()
                Log.d(LOG, "Failed to delete item")
            }
    }
}