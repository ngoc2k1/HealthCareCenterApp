package com.example.myapplication.serviceapi

import com.example.myapplication.prefs.HawkKey
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor
import okhttp3.Response

object PatientHeaderAuthorizationInterceptor  : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val jwt = Hawk.get<String>(HawkKey.ACCESS_TOKEN_PATIENT)
        val headers = request.headers.newBuilder().add("authorization", jwt ?: "null").build()
        request = request.newBuilder().headers(headers).build()
        return chain.proceed(request)
    }
}