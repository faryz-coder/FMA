package com.bmit.fma.firebaseUtils

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UpdateData {
    private val db = Firebase.firestore

    fun updateStaffInfo(id: String, address: String, staffHandler: String, name: String, phoneNo: String) {
        val data = hashMapOf(
            "name" to name,
            "handler" to staffHandler,
            "phoneNo" to phoneNo,
            "address" to address
        )

        db.collection("Login").document(id)
            .set(data, SetOptions.merge())
    }
}