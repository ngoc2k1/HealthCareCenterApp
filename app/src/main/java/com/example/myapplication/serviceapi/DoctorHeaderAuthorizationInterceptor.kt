package com.example.myapplication.serviceapi

import android.util.Log
import com.example.myapplication.prefs.HawkKey
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor
import okhttp3.Response

object DoctorHeaderAuthorizationInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val jwt = Hawk.get<String>(HawkKey.ACCESS_TOKEN_DOCTOR)
        val headers = request
            .newBuilder()
            .header("Authorization",  "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiRE9DVE9SIiwic3ViIjoiMiIsImlhdCI6MTcwMTE4NzE5NiwiZXhwIjoxNzAxNTQ3MTk2fQ.TyXbRB1C7qZqtUsgUwihsjTlr_L2wTzcydi88wdPPpqp9G925JdDyBV_YV3fy6MFxpIjLrHWXdIKgmpIOqixxw").build()
            .method(request.method,request.body).
//        request = request.newBuilder().headers(headers).build()
        return chain.proceed(request)
    }
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val originalRequest = chain.request()
//        val token = Hawk.get(HawkKey.ACCESS_TOKEN_DOCTOR, "nothing")
//
//        val newRequest = originalRequest.newBuilder()
//            .addHeader("Authorization", token)
//            .build()
//        return chain.proceed(newRequest)
//    }
}
