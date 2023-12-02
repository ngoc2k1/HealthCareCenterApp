package com.example.myapplication.serviceapi

import com.example.myapplication.model.ChangePwRequest
import com.example.myapplication.model.ChangePwResponse
import com.example.myapplication.model.DoctorNotificationResponse
import com.example.myapplication.model.DoctorProfileRequest
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
    suspend fun getDoctor(): DoctorAccountResponse
    @PUT("doctor/update")
    suspend fun updateDoctor(@Body doctorProfileRequest: DoctorProfileRequest): ChangePwResponse

    @POST("doctor/login")
    suspend fun loginDoctor(
        @Body doctorLoginRequest: UserLoginRequest
    ): UserLoginResponse

//    @PUT("doctor/update/{id}")
//    suspend fun updateDoctor(@Path("id") id: Int, @Body doctorModel: DoctorModel): Boolean

    @GET("doctor/notification")
    suspend fun getNotification(): DoctorNotificationResponse//

    @GET("list-patient")
    suspend fun getListPatient(@Query("name") name: String): PatientListResponse//

    @PUT("book-schedule/confirm/{id}")
    suspend fun confirmPatientTested(@Path("id") id: Int): Boolean

    @GET("list-book-schedule-by-doctor?page=1")
    suspend fun getListBookScheduleByDoctor(): WorkScheduleResponse//

    @GET("list-book-schedule-by-doctor?page=1")
    suspend fun updateMedicalHistory(): WorkScheduleResponse

    @PUT("doctor/change-password")
    suspend fun changePassword(@Body changePwRequest: ChangePwRequest): ChangePwResponse//

    @PUT("doctor/reset-password")
    suspend fun sendNewPassword(@Body resetPwRequest: ResetPwRequest): ChangePwResponse//

    @POST("doctor/reset-password")
    suspend fun sendEmail(@Body reqPwActiveRequest: ResetPwActiveRequest): ChangePwResponse//
}