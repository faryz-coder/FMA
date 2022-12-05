package com.bmit.fma.student

import com.google.firebase.Timestamp

data class ListOrder (
    val order: String,
    val status: String,
    val total: String,
    val date: Timestamp,
    val orderId: String
)
