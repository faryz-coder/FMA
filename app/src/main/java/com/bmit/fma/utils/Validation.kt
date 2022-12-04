package com.bmit.fma.utils

import android.net.Uri
import android.util.Log
import com.bmit.fma.FixNotation.LOG
import com.google.firebase.firestore.DocumentSnapshot

class Validation {

    fun validateAddItem(imgUrl: Uri?, name: String, calories: String, price: String, type: String, status: String): Boolean {
        return (imgUrl != null && !name.isNullOrEmpty() && !calories.isNullOrEmpty() && !price.isNullOrEmpty() && !type.isNullOrEmpty() && !status.isNullOrEmpty())
    }

    fun validateToUpdateItem(
        item: DocumentSnapshot,
        name: String,
        calories: String,
        price: String,
        type: String,
        status: String
    ) : Boolean {
        return !(item["name"].toString() == name && item["calories"].toString() == calories && item["price"].toString() == price && item["type"].toString() == type && item["status"] == status)
    }
}