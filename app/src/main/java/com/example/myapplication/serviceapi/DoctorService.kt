package com.example.myapplication.serviceapi

import com.example.myapplication.model.NotificationModel
import com.example.myapplication.model.doctor.DoctorAccountResponse
import com.example.myapplication.model.doctor.DoctorLoginRequest
import com.example.myapplication.model.doctor.DoctorLoginResponse
import com.example.myapplication.model.doctor.DoctorModel
import com.example.myapplication.model.patient.BookSchedule
import com.example.myapplication.model.patient.PatientLoginRequest
import com.example.myapplication.model.patient.PatientModel
import retrofit2.http.*

interface DoctorService {
//    @Query("security") security: String,
//    @Header("Accept-language") acceptLanguage: String,
    @GET("doctor")
    suspend fun getDoctor(): DoctorAccountResponse

    @POST("doctor/login")
    suspend fun loginDoctor(
        @Body doctorLoginRequest: DoctorLoginRequest
    ): DoctorLoginResponse

    @PUT("doctor/update/{id}")
    suspend fun updateDoctor(@Path("id") id: Int, @Body doctorModel: DoctorModel): Boolean

    @GET("doctor/notification")
    suspend fun getNotification(): List<NotificationModel>

    @GET("list-patient")
    suspend fun getListPatient(): List<PatientModel>

    @PUT("book-schedule/confirm/{id}")
    suspend fun confirmPatientTested(@Path("id") id: Int): Boolean

    @GET("doctor/list-book-schedule-by-doctor/{id}")
    suspend fun getDoctorScheduleList(@Path("id") id: Int): List<BookSchedule>

}