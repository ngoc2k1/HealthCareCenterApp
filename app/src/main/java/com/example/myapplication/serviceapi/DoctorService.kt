package com.example.myapplication.serviceapi

import com.example.myapplication.model.NotificationModel
import com.example.myapplication.model.doctor.DoctorModel
import com.example.myapplication.model.doctor.WorkScheduleDoctor
import com.example.myapplication.model.patient.BookSchedule
import com.example.myapplication.model.patient.MedicalHistory
import com.example.myapplication.model.patient.PatientModel
import com.example.myapplication.utils.STATUS_BOOK
import retrofit2.http.*

interface DoctorService {
    @GET("doctor")
    suspend fun getDoctor(): DoctorModel

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