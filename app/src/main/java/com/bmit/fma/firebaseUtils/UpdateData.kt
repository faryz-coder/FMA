package com.bmit.fma.firebaseUtils

import android.net.Uri
import android.util.Log
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.canteen.ItemCallback
import com.google.android.material.snackbar.Snackbar
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

    fun updateItemInfo(
        itemId: String,
        imageUri: Uri?,
        name: String,
        calories: String,
        price: String,
        type: String,
        status: String,
        callback: ItemCallback) {

        val data = hashMapOf(
            "name" to name,
            "calories" to calories,
            "price" to price,
            "type" to type,
            "status" to status
        )

        db.collection("canteen")
            .document("menu")
            .collection("list")
            .document(itemId)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                if (imageUri == null) {
                    callback.onItemUpdated()
                } else {
                    uploadImage(itemId, imageUri, callback)
                }
            }
    }

    private fun uploadImage(itemId: String, imageUri: Uri?, callback: ItemCallback) {
        // upload image
        val storageRef = storage.reference.child(itemId)
        val uploadImg = storageRef.putFile(imageUri!!)

        val urlTask = uploadImg.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val imageUploadURL = task.result.toString()
                Log.d(LOG, "image Upload Success : $imageUploadURL")
                updateItemImage(itemId,imageUploadURL, callback)
            } else {
                Log.d(LOG, "image Upload Failed")
                return@addOnCompleteListener
            }
        }
    }

    private fun updateItemImage(itemId: String, imageUploadURL: String, callback: ItemCallback) {
        // update db
        val data = hashMapOf(
            "imageUrl" to imageUploadURL
        )

        db.collection("canteen")
            .document("menu")
            .collection("list")
            .document(itemId)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                callback.onItemUpdated()
            }

    }
}