package com.example.myapplication.serviceapi

import com.example.myapplication.prefs.HawkKey
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

object DoctorHeaderAuthorizationInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
        request = request.newBuilder()
            .addHeader("Authorization", "Bearer ${Hawk.get<String>(HawkKey.ACCESS_TOKEN_DOCTOR)}")
            .url(url.build())
            .build()

        return chain.proceed(request)
    }}
