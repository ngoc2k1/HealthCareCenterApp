package com.example.myapplication.firebase

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
object Client {
    var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                field = Retrofit.Builder()
                    .baseUrl("https://fcm.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return field
        }
}

interface APIService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=AAAAaNUNAYw:APA91bHmncb_267EKuu9YlaKEPBrS6yg_WCi4-t704uDcYhq3Cf_fSWwxvi2W3aVhZTW-Vo0_oc4PCIXKUXH_9Bx827id7debb9M4i_Fz9vUDHDc8VnH8ugcHeh0U66eCrrBXDG2cpy4"
    )//server-key
    @POST("fcm/send")
    fun sendNotification(@Body body: FCMSender?): retrofit2.Call<FCMResponse?>?
}
