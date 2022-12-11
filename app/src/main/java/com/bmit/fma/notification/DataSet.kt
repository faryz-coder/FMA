package com.bmit.fma.notification

data class DataSet(
    val body: String,
    val title: String
)

data class Message(
    var to:String,
    var notification : Notification,
    var data:DataSet,
    var direct_boot_ok: Boolean = true
)

data class Notification(
    val title: String,
    val body: String
)
