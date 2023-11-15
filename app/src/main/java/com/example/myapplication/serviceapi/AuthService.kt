package com.example.myapplication.serviceapi

import com.example.myapplication.model.patient.PatientLoginRequest
import com.example.myapplication.model.patient.PatientModel
import retrofit2.http.*

interface AuthService {
    @POST("auth/patient/login")
    suspend fun loginPatient(@Body patientLoginRequest: PatientLoginRequest): Boolean

    @POST("auth/patient/register")
    suspend fun registerPatient(@Body patientModel: PatientModel): Boolean

    @POST("auth/patient/change-password")
    suspend fun changePasswordPatient(@Body patientLoginRequest: PatientLoginRequest): Boolean

    @POST("auth/patient/forgot-password")
    suspend fun forgotPasswordPatient(@Field("phone") phone: String): Boolean

    @POST("auth/doctor/login")
    suspend fun loginDoctor(@Body docLoginRequest: PatientLoginRequest): Boolean

    @POST("auth/doctor/forgot-password")
    suspend fun forgotPasswordDoctor(@Query("phone") phone: String): Boolean

    @POST("auth/doctor/change-password")
    suspend fun changePasswordDoctor(@Body patientLoginRequest: PatientLoginRequest): Boolean

    @POST("auth/logout")
    suspend fun logOut( ): Boolean

}