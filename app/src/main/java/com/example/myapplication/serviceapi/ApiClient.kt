package com.example.myapplication.serviceapi

import android.content.Context
import com.google.gson.Gson
import com.google.zxing.client.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(context: Context) {
    private val gson = Gson()
    private val BASE_URL = "http://192.168.0.102:8080/api/v1/"

    val okHttpClientDoctor = OkHttpClient.Builder()
        .addInterceptor(NetworkInterceptor(context))
        .addInterceptor(createHttpLoggingInterceptor()) // Log lỗi ra logcat: header lỗi, connect thành công
        .addInterceptor(DoctorHeaderAuthorizationInterceptor)
        .build()

    val retrofitDoctor = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .client(okHttpClientDoctor)
        .build()

    val okHttpClientPatient = OkHttpClient.Builder()
        .addInterceptor(NetworkInterceptor(context))
        .addInterceptor(createHttpLoggingInterceptor()) // Log lỗi ra logcat: header lỗi, connect thành công
        .addInterceptor(PatientHeaderAuthorizationInterceptor)
        .build()

    val retrofitPatient = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .client(okHttpClientPatient)
        .build()
    val patientService: PatientService by lazy {
        retrofitPatient.create(PatientService::class.java)
    }
    val doctorService: DoctorService by lazy {
        retrofitDoctor.create(DoctorService::class.java)
    }

    private fun createHttpLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }
}