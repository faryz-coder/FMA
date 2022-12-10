package com.bmit.fma.utils

import android.app.Activity
import android.content.Intent

import com.bmit.fma.LoginActivity

class Common {
    fun logout(activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
        activity.finish()
    }
}