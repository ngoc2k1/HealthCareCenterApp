package com.example.myapplication.serviceapi

import com.example.myapplication.model.BookScheduleDetailResponse
import com.example.myapplication.model.ChangePwRequest
import com.example.myapplication.model.ChangePwResponse
import com.example.myapplication.model.DoctorNotificationResponse
import com.example.myapplication.model.DoctorProfileRequest
import com.example.myapplication.model.MedicalHistoryDetailResponse
import com.example.myapplication.model.MedicalHistoryListDoctorResponse
import com.example.myapplication.model.MedicalHistoryUpdateRequest
import com.example.myapplication.model.PatientAccountResponse
import com.example.myapplication.model.PatientListResponse
import com.example.myapplication.model.ResetPwActiveRequest
import com.example.myapplication.model.ResetPwRequest
import com.example.myapplication.model.WorkScheduleResponse
import com.example.myapplication.model.doctor.DoctorAccountResponse
import com.example.myapplication.model.doctor.UserLoginRequest
import com.example.myapplication.model.doctor.UserLoginResponse
import retrofit2.http.*

interface DoctorService {
    @GET("doctor")
    suspend fun getDoctor(): Resource<DoctorAccountResponse>

    @PUT("doctor/update")
    suspend fun updateDoctor(@Body doctorProfileRequest: DoctorProfileRequest): Resource<ChangePwResponse>

    @POST("doctor/login")
    suspend fun loginDoctor(@Body doctorLoginRequest: UserLoginRequest): Resource<UserLoginResponse>

    @GET("doctor/notification")
    suspend fun getNotification(@Query("page") page: Int): Resource<DoctorNotificationResponse>

    @GET("list-patient")
    suspend fun getListPatient(@Query("name") name: String): Resource<PatientListResponse>

    @PUT("book-schedule/confirm/{id}")
    suspend fun confirmPatientTested(@Path("id") id: Int): Resource<ChangePwResponse>

    @GET("list-book-schedule-by-doctor")
    suspend fun getListBookScheduleByDoctor(@Query("page") page: Int): Resource<WorkScheduleResponse>

    @PUT("doctor/change-password")
    suspend fun changePassword(@Body changePwRequest: ChangePwRequest): Resource<ChangePwResponse>

    @PUT("doctor/reset-password")
    suspend fun sendNewPassword(@Body resetPwRequest: ResetPwRequest): Resource<ChangePwResponse>

    @POST("doctor/reset-password")
    suspend fun sendEmail(@Body reqPwActiveRequest: ResetPwActiveRequest): Resource<ChangePwResponse>

    @GET("book-schedule/detail/{id}")
    suspend fun getDetailBookSchedule(@Path("id") id: Int): Resource<BookScheduleDetailResponse>

    @GET("list-medical-history-by-doctor/{id}")
    suspend fun getListMedicalHistoryByDoctor(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): Resource<MedicalHistoryListDoctorResponse>

    @PUT("medical-history/update/{id}")
    suspend fun updateMedicalHistory(
        @Path("id") id: Int,
        @Body medicalHistoryUpdateRequest: MedicalHistoryUpdateRequest
    ): Resource<ChangePwResponse>

    @GET("patient/{id}")
    suspend fun getPatientByDoctor(@Path("id") id: Int): Resource<PatientAccountResponse>
}