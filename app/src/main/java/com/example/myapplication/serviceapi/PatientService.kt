package com.example.myapplication.serviceapi

import com.example.myapplication.model.PatientAccountResponse
import com.example.myapplication.model.PatientNotificationResponse
import com.example.myapplication.model.doctor.UserLoginRequest
import com.example.myapplication.model.doctor.UserLoginResponse
import retrofit2.http.*

interface PatientService {
    @GET("patient")
    suspend fun getPatient(): PatientAccountResponse

    @POST("patient/login")
    suspend fun loginPatient(
        @Body patientLoginRequest: UserLoginRequest
    ): UserLoginResponse

//    @PUT("patient/update/{id}")
//    suspend fun updatePatient(@Path("id") id: Int, @Body patientModel: PatientModel): Boolean

    @GET("patient/notification")
    suspend fun getNotification(): PatientNotificationResponse

//    @GET("list-medical-history")
//    suspend fun getListMedicalHistory(): List<MedicalHistory>

//    @GET("medical-history/detail/{id}")
//    suspend fun getMedicalHistory(@Path("id") id: Int): MedicalHistory

//    @PUT("medical-history/update/{id}")
//    suspend fun updateMedicalHistory(
//        @Path("id") id: Int,
//        @Body medicalHistory: MedicalHistory
//    ): Boolean

//    @POST("medical-history/create")
//    suspend fun createMedicalHistory(@Body medicalHistory: MedicalHistory): Boolean

//    @GET("list-specialty")
//    suspend fun getListSpecialty(): List<Specialty>

//    @GET("doctor-by-specialty/{id}")
//    suspend fun getDoctorBySpecialty(@Path("id") id: Int): List<DoctorModel>

//    @GET("date-by-doctor/{id}")
//    suspend fun getDateByDoctor(@Path("id") id: Int): List<PatientModel>

//    @GET("list-book-schedule")
//    suspend fun getListBookSchedule(): List<BookSchedule>

//    @PUT("book-schedule/update/{id}")
//    suspend fun updateBookSchedule(@Path("id") id: Int, @Body bookSchedule: BookSchedule): Boolean

//    @POST("book-schedule/create")
//    suspend fun createBookSchedule(@Body bookSchedule: BookSchedule): Boolean

//    @GET("book-schedule/detail/{id}")
//    suspend fun getBookSchedule(@Path("id") id: Int): BookSchedule

}