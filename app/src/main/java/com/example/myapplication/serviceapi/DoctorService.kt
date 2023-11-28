package com.example.myapplication.serviceapi

import com.example.myapplication.model.NotificationModel
import com.example.myapplication.model.doctor.DoctorAccountResponse
import com.example.myapplication.model.doctor.DoctorLoginRequest
import com.example.myapplication.model.doctor.DoctorLoginResponse
import com.example.myapplication.model.doctor.DoctorModel
import com.example.myapplication.model.doctor.DoctorSpecialtyResponse
import com.example.myapplication.model.patient.BookSchedule
import com.example.myapplication.model.patient.PatientLoginRequest
import com.example.myapplication.model.patient.PatientModel
import retrofit2.http.*

interface DoctorService {
//    @Query("security") security: String,
//    @Header("Accept-language") acceptLanguage: String,
//    @Header("Authorization: eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiRE9DVE9SIiwic3ViIjoiMiIsImlhdCI6MTcwMTE4NzE5NiwiZXhwIjoxNzAxNTQ3MTk2fQ.TyXbRB1C7qZqtUsgUwihsjTlr_L2wTzcydi88wdPPpqp9G925JdDyBV_YV3fy6MFxpIjLrHWXdIKgmpIOqixxw")
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