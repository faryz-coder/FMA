package com.bmit.fma.firebaseUtils

import android.net.Uri
import android.util.Log
import android.view.View
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UploadData(loginViewModel: LoginViewModel, requireView: View) {
    private val storage = Firebase.storage
    private val db = Firebase.firestore
    val login = loginViewModel
    private val view = requireView
    /**
     * Upload item details and image
     */
    fun uploadItem(
        imageUri: Uri?,
        name: String,
        calories: String,
        price: String,
        type: String,
        status: String
    ) : Boolean {
        var success = false
        val data = hashMapOf(
            "name" to name,
            "calories" to calories,
            "price" to price,
            "type" to type,
            "status" to status
        )

        // upload data
        db.collection("canteen")
            .document(login.email)
            .collection("menu")
            .add(data)
            .addOnSuccessListener { document ->
                // upload image
                val storageRef = storage.reference.child(document.id)
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

                        // update db
                        val data = hashMapOf(
                            "imageUrl" to imageUploadURL
                        )

                        db.collection("canteen")
                            .document(login.email)
                            .collection("menu")
                            .document(document.id)
                            .set(data, SetOptions.merge())
                            .addOnSuccessListener {
                                success = true
                                Snackbar.make(view, "Data Uploaded", Snackbar.LENGTH_SHORT).show()
                            }

                    } else {
                        Log.d(LOG, "image Upload Failed")
                        Snackbar.make(view, "Image Upload failed", Snackbar.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                }

            }
            .addOnFailureListener {
                Log.d(LOG, "upload img failed: $it")
                Snackbar.make(view, "Data Failed to Upload", Snackbar.LENGTH_SHORT).show()
            }
        return success
    }

}