package com.bmit.fma.firebaseUtils

import android.net.Uri
import android.util.Log
import com.bmit.fma.FixNotation
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.interfaceListener.ItemCallback
import com.bmit.fma.student.ListMenu
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson

class UpdateData {
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private val notificationUtil = NotificationUtil()

    fun updateStaffInfo(
        id: String,
        address: String,
        staffHandler: String,
        name: String,
        phoneNo: String,
        callback: ItemCallback
    ) {
        val data = hashMapOf(
            "name" to name,
            "handler" to staffHandler,
            "phoneNo" to phoneNo,
            "address" to address
        )

        db.collection("Login").document(id)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                callback.onItemUpdated()
            }
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

    fun removeOrder(id: String, studentId: String, callback: ItemCallback) {
        val data = hashMapOf(
            "status" to "canceled"
        )
        db.collection("student").document(studentId).collection("order").document(id)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                // delete from canteee
                db.collection("canteen").document("order").collection("list").document(id)
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        // delete success
                        callback.itemRemoved(id)
                    }
            }
    }

    fun removeUser(id: String, callback: ItemCallback) {
        db.collection("Login")
            .document(id)
            .delete()
            .addOnSuccessListener {
                callback.itemRemoved(id)
            }
            .addOnFailureListener {
                callback.removedFailed()
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
        callback: ItemCallback
    ) {

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

    fun updateStudentInfo(itemId: String, studentId: String, name: String, password: String, callback: ItemCallback) {
        val data = hashMapOf(
            "studentId" to studentId,
            "name" to name,
            "type" to FixNotation.STUDENT,
            "password" to password
        )

        db.collection("Login").document(itemId)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                Log.d(LOG,"Student Added: \n $data")
                callback.onItemUpdated()
            }
            .addOnFailureListener { e ->
                Log.d(LOG,"Failed to Add Student")
            }
    }

    fun confirmPayment(
        orderList: MutableList<ListMenu>,
        total: Double?,
        studentId: String,
        orderDate: String,
        callback: ItemCallback
    ) {
        val orderJson = Gson().toJson(orderList)
//        val toList = Gson().fromJson(json, Array<ListMenu>::class.java)
        val data = hashMapOf(
            "date" to orderDate,
            "studentId" to studentId,
            "order" to orderJson,
            "status" to "order confirmed",
            "total" to total
        )
        db.collection("canteen").document("order").collection("list")
            .add(data)
            .addOnSuccessListener {

                db.collection("student").document(studentId).collection("order").document(it.id)
                    .set(data)
                    .addOnSuccessListener {
                        Log.d(LOG, "Payment confirmed and order submit: $it")
                        callback.onItemUpdated()
                    }
            }
            .addOnFailureListener {
                Log.d(LOG, "Failed to submit order: $it")
            }

    }

    fun updateOrderStatus(orderId: String, studentId: String, callback: ItemCallback) {
        var orderStatus = ""
        db.collection("canteen").document("order").collection("list").document(orderId)
            .get()
            .addOnSuccessListener { document ->
                when (document["status"].toString()) {
                    "order confirmed" -> orderStatus = "order processed"
                    "order processed" -> orderStatus = "ready to pickup"
                }
                if (document["status"].toString() != "ready to pickup") {
                    val data = hashMapOf(
                        "status" to orderStatus
                    )
                    db.collection("canteen").document("order").collection("list").document(orderId)
                        .set(data, SetOptions.merge())
                        .addOnSuccessListener {
                            db.collection("student").document(studentId).collection("order").document(orderId)
                                .set(data, SetOptions.merge())
                                .addOnSuccessListener {
                                    getStudentTokenAndNotify(studentId, orderStatus)
                                    callback.orderStatusUpdated(orderId, orderStatus)
                                    Log.d(LOG, "updateOrderStatus: $orderStatus")
                                }
                        }
                } else {
                    callback.orderStatusUpdated(orderId, "")
                }

            }

    }

    fun updateUserToken(token: String, studentId: String) {
        val data = hashMapOf(
            "token" to token
        )
        db.collection("Login").whereEqualTo("studentId", studentId)
            .get()
            .addOnSuccessListener { document ->
                db.collection("Login").document(document.first().id)
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d(LOG, "user token updated : $studentId")
                    }
            }
    }

    private fun getStudentTokenAndNotify(studentId: String, orderStatus: String) {
        db.collection("Login").whereEqualTo("studentId", studentId)
            .get()
            .addOnSuccessListener { document ->
                val token = document.first().getField<String>("token").toString()
                notificationUtil.notifyUser(token, orderStatus)
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