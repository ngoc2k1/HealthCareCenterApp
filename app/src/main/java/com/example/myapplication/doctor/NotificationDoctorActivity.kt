package com.example.myapplication.doctor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityDoctorNotificationBinding
import com.example.myapplication.model.DoctorNotificationResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorNotificationBinding
    private var mListNotification: List<DoctorNotificationResponse.Data> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@NotificationDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        val apiClient = ApiClient(this@NotificationDoctorActivity)

        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val notificationResponse = apiClient.doctorService.getNotification()
                if (notificationResponse.code == 200) {
                    withContext(Dispatchers.Main)
                    {
                        mListNotification = notificationResponse.data
                        toast(mListNotification.size.toString())
                        if (mListNotification.isEmpty()) {
                            tvNoneNotification.visible()
                            rvNotification.gone()
                        } else {
                            tvNoneNotification.gone()
                            rvNotification.visible()
                            val notificationItemAdapter = DoctorListNotificationItemmAdapter()
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                            rvNotification.adapter = notificationItemAdapter
                            notificationItemAdapter.submitList(mListNotification)
                        }
                    }
                }
            }
        }
    }
}