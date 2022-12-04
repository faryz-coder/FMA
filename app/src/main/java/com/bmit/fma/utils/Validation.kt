package com.bmit.fma.utils

import android.net.Uri

class Validation {

    fun validateAddItem(imgUrl: Uri?, name: String, calories: String, price: String, type: String, status: String): Boolean {
        return (imgUrl != null && !name.isNullOrEmpty() && !calories.isNullOrEmpty() && !price.isNullOrEmpty() && !type.isNullOrEmpty() && !status.isNullOrEmpty())
    }
}