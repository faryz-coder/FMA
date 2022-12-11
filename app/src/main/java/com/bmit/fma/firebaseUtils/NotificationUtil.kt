package com.bmit.fma.firebaseUtils

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.bmit.fma.FixNotation.LOG
import com.bmit.fma.R
import com.bmit.fma.notification.DataSet
import com.bmit.fma.notification.Message
import com.bmit.fma.notification.Notification
import com.bmit.fma.notification.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotificationUtil {

    fun notifyUser(token: String, orderStatus: String, requireContext: Context) {
        sendNotification(token, orderStatus, requireContext)
    }

    fun updateToken(token: String, studentId: String) {
        val updateData = UpdateData()
        updateData.updateUserToken(token, studentId)
    }

    private fun sendNotification(token: String, orderStatus: String, requireContext: Context) = CoroutineScope(Dispatchers.IO).launch {
        val title: String = requireContext.getString(R.string.app_name)

        val body = requireContext.getString(R.string.notification_body) + orderStatus
        try {
            val tok = "fFx3SGFeS3ynuyewGYt6t1:APA91bFSAcRjHUrMSDgmi86IKNtPNgcJ_nGkQiLrcvJZyJdwj5MBQgLwiisxUzTvyD4J_u8VdLgwjAX95PFXIUFmh_dtNpCREvpApW4CptxQeVyd0NaOWWz2IZAhgdbBgMLIcESKlZHx"
            val msg = Message(token, Notification(title,body), DataSet("",""))
            val response = RetrofitInstance.api.postNotification(msg)
            if (response.isSuccessful) {
                Log.d(LOG, response.message())
            } else {
                //here i get 404
                Log.e(LOG, response.code().toString())
            }
        } catch (e: Exception) {
            Log.e(LOG, e.message.toString())
        }

    }
}