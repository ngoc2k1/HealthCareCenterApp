package com.example.myapplication.serviceapi

import com.example.myapplication.prefs.HawkKey
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor
import okhttp3.Response

object DoctorHeaderAuthorizationInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val jwt = Hawk.get<String>(HawkKey.ACCESS_TOKEN_DOCTOR)
        val headers = request.headers.newBuilder().add("Authorization", jwt ?: "null").build()
        request = request.newBuilder().headers(headers).build()
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
