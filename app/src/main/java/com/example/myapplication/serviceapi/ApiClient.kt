package com.example.myapplication.serviceapi

import com.example.myapplication.utils.Constant
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val gson = Gson()
    const val BASE_URL = "192.168.0.102:8080/api/v1/"

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .build()

    val doctorService: DoctorService by lazy {
        retrofit.create(DoctorService::class.java)
    }
    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
    val patientService: PatientService by lazy {
        retrofit.create(PatientService::class.java)
    }
}