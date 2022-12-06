package com.bmit.fma.notification

import com.bmit.fma.notification.Constants.Companion.CONTENT_TYPE
import com.bmit.fma.notification.Constants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body message: Message
    ): Response<ResponseBody>
}