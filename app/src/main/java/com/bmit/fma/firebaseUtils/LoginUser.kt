package com.bmit.fma.firebaseUtils

import com.bmit.fma.interfaceListener.LoginCallback
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginUser {
    private val db = Firebase.firestore

    fun studentLogin(studentId: String, password: String, callback: LoginCallback) {
        db.collection("Login").whereEqualTo("type", "student")
            .get()
            .addOnSuccessListener { document ->
                document.forEach {
                    if (it["studentId"].toString() == studentId && it["password"].toString() == password) {
                        callback.isStudent(true, studentId, it["type"].toString())
                        return@addOnSuccessListener
                    }
                }
                callback.isStudent(false, "", "")
            }
    }

    fun adminLogin(adminEmail: String, password: String, callback: LoginCallback) {
        db.collection("Login").whereEqualTo("type", "admin")
            .get()
            .addOnSuccessListener { document ->
                document.forEach {
                    if (it["email"].toString() == adminEmail && it["password"].toString() == password) {
                        callback.isAdmin(true, adminEmail, it["type"].toString())
                        return@addOnSuccessListener
                    }
                }
                callback.isAdmin(false, "", "")
            }
    }

    fun canteenLogin(staffEmail: String, password: String, callback: LoginCallback) {
        db.collection("Login").whereEqualTo("type", "staff")
            .get()
            .addOnSuccessListener { document ->
                document.forEach {
                    if (it["email"].toString() == staffEmail && it["password"].toString() == password) {
                        callback.isCanteen(true, staffEmail, it["type"].toString())
                        return@addOnSuccessListener
                    }
                }
                callback.isCanteen(false, "", "")
            }
    }

}