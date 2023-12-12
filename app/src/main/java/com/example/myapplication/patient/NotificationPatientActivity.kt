package com.example.myapplication.patient

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityDoctorNotificationBinding
import com.example.myapplication.databinding.FragmentNotificationBinding
import com.example.myapplication.doctor.DoctorListNotificationItemmAdapter
import com.example.myapplication.model.DoctorNotificationResponse
import com.example.myapplication.model.MedicalHistoryListDoctorResponse
import com.example.myapplication.model.PatientNotificationResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationPatientActivity : AppCompatActivity() {
    private lateinit var binding: FragmentNotificationBinding
    private var mListNotification: List<PatientNotificationResponse.Data> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@NotificationPatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        val apiClient = ApiClient(this@NotificationPatientActivity)
        val notificationItemAdapter = PatientListNotificationItemmAdapter()

        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val notificationResponse = apiClient.patientService.getNotification(1)
                withContext(Dispatchers.Main) {
                    if (notificationResponse.isSuccessful()) {
                        notificationResponse.data?.data?.let {
                            it.apply {
                                mListNotification = it
                                if (mListNotification.isEmpty()) {
                                    tvNoneNotification.visible()
                                    rvNotification.gone()
                                } else {
                                    tvNoneNotification.gone()
                                    rvNotification.visible()
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                                    rvNotification.adapter = notificationItemAdapter
                                    notificationItemAdapter.submitList(mListNotification)
                                }
                            }
                        }
                    } else {
                        toast(notificationResponse.error?.error?.msg.toString())
                    }
                }
            }
        }
    }
}