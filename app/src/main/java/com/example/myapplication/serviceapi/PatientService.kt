package com.example.myapplication.serviceapi

import com.example.myapplication.model.BookCreatedRequest
import com.example.myapplication.model.BookScheduleByPatientResponse
import com.example.myapplication.model.BookScheduleDetailResponse
import com.example.myapplication.model.ChangePwRequest
import com.example.myapplication.model.ChangePwResponse
import com.example.myapplication.model.DateByDoctor
import com.example.myapplication.model.DoctorBySpecialtyResponse
import com.example.myapplication.model.PatientNotificationResponse
import com.example.myapplication.model.MedicalHistoryDetailResponse
import com.example.myapplication.model.MedicalHistoryListPatientResponse
import com.example.myapplication.model.PatientAccountResponse
import com.example.myapplication.model.PatientRegisterRequest
import com.example.myapplication.model.PatientUpdateRequest
import com.example.myapplication.model.ResetPwActiveRequest
import com.example.myapplication.model.ResetPwRequest
import com.example.myapplication.model.SpecialtyListResponse
import com.example.myapplication.model.TimeByDoctorResponse
import com.example.myapplication.model.UserLoginRequest
import com.example.myapplication.model.UserLoginResponse
import retrofit2.http.*

interface PatientService {
    @GET("patient")
    suspend fun getPatient(): Resource<PatientAccountResponse>

    @PUT("patient/update")
    suspend fun updatePatient(@Body patientUpdateRequest: PatientUpdateRequest): Resource<ChangePwResponse>

    @GET("doctor-by-specialty/{id}")
    suspend fun getDoctorBySpecialty(
        @Path("id") id: Int, @Query("page") page: Int
    ): Resource<DoctorBySpecialtyResponse>

    @GET("list-specialty")
    suspend fun getListSpecialty(): Resource<SpecialtyListResponse>

    @GET("time-by-doctor/{id}")
    suspend fun getTimeByDoctor(
        @Path("id") id: Int,
        @Query("date") date: String
    ): Resource<TimeByDoctorResponse>

    @GET("date-by-doctor/{id}")
    suspend fun getDateByDoctor(@Path("id") id: Int): Resource<DateByDoctor>

    @POST("patient/login")
    suspend fun loginPatient(@Body patientLoginRequest: UserLoginRequest): Resource<UserLoginResponse>

    @POST("patient/register")
    suspend fun registerPatient(@Body patientRegisterRequest: PatientRegisterRequest): Resource<ChangePwResponse>

    @GET("patient/notification")
    suspend fun getNotification(@Query("page") page: Int): Resource<PatientNotificationResponse>

    @PUT("book-schedule/cancel/{id}")
    suspend fun cancelBookSchedule(@Path("id") id: Int): Resource<ChangePwResponse>

    @PUT("book-schedule/update/{id}")
    suspend fun updateBookSchedule(
        @Path("id") id: Int,
        @Body bookScheduleRequest: BookCreatedRequest
    ): Resource<ChangePwResponse>

    @GET("list-book-schedule-by-patient")
    suspend fun getListBookScheduleByPatient(@Query("page") page: Int): Resource<BookScheduleByPatientResponse>

    @PUT("patient/change-password")
    suspend fun changePassword(@Body changePwRequest: ChangePwRequest): Resource<ChangePwResponse>

    @PUT("patient/reset-password")
    suspend fun sendNewPassword(@Body resetPwRequest: ResetPwRequest): Resource<ChangePwResponse>

    @POST("patient/reset-password")
    suspend fun sendEmail(@Body reqPwActiveRequest: ResetPwActiveRequest): Resource<ChangePwResponse>

    @GET("book-schedule/detail/{id}")
    suspend fun getDetailBookSchedule(@Path("id") id: Int): Resource<BookScheduleDetailResponse>

    @POST("book-schedule/create")
    suspend fun createBookSchedule(@Body bookScheduleRequest: BookCreatedRequest): Resource<ChangePwResponse>

    @GET("list-medical-history-by-patient")
    suspend fun getListMedicalHistoryByPatient(
        @Query("page") page: Int
    ): Resource<MedicalHistoryListPatientResponse>

    @GET("medical-history/detail/{id}")
    suspend fun getDetailMedicalHistory(@Path("id") id: Int): Resource<MedicalHistoryDetailResponse>
}