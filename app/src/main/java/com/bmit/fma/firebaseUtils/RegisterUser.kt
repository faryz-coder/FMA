package com.bmit.fma.firebaseUtils

import android.util.Log
import android.view.View
import com.bmit.fma.FixNotation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterUser {

    var view : View
    private val db = Firebase.firestore

    constructor(myView: View) {
        view = myView
    }
    /**
     * Insert Staff Cred into Firebase DB
     */
    fun registerStaff(email: String, name: String, staffHandler: String, address: String, phoneNo: String, password: String) {
        val data = hashMapOf(
            "email" to email,
            "name" to name,
            "handler" to staffHandler,
            "address" to address,
            "phoneNo" to phoneNo,
            "type" to FixNotation.STAFF,
            "password" to password
        )

        db.collection("Login")
            .add(data)
            .addOnSuccessListener {
                Snackbar.make(view, "Staff Added! ${data["name"]}", Snackbar.LENGTH_SHORT).show()
                Log.d(FixNotation.LOG,"Staff Added: \n $data")
            }
            .addOnFailureListener { e ->
                Snackbar.make(view, "Failed! ${data["name"]}", Snackbar.LENGTH_SHORT).show()
            }
    }

    /**
     * Insert Student Cred into Firebase DB
     */
    fun registerStudent(studentId: String, name: String, password: String) {
        val data = hashMapOf(
            "studentId" to studentId,
            "name" to name,
            "type" to FixNotation.STUDENT,
            "password" to password
        )

        db.collection("Login")
            .add(data)
            .addOnSuccessListener {
                Snackbar.make(view, "Student Added! ${data["name"]}", Snackbar.LENGTH_SHORT).show()
                Log.d(FixNotation.LOG,"Student Added: \n $data")
            }
            .addOnFailureListener { e ->
                Snackbar.make(view, "Failed! ${data["name"]}", Snackbar.LENGTH_SHORT).show()
            }
    }

}