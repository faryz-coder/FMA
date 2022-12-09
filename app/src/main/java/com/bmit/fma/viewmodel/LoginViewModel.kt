package com.bmit.fma.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    var email : String = "canteen@gmail.com"
    var type : String = "canteen"
    var studentId : String = "123"

    fun setStudent(intent: Intent) {
        studentId = intent.getStringExtra("id").toString()
        type = intent.getStringExtra("type").toString()
    }

    fun setCanteenAndAdmin(intent: Intent) {
        email = intent.getStringExtra("id").toString()
        type = intent.getStringExtra("type").toString()
    }

}