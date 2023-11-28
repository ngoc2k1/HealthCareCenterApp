package com.example.myapplication.doctor

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorAccountBinding
import com.example.myapplication.model.doctor.DoctorLoginRequest
import com.example.myapplication.prefs.HawkKey
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.toast
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@AccountDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        var apiClient = ApiClient(this@AccountDoctorActivity)
        toast(
            Hawk.get<String>(HawkKey.ACCESS_TOKEN_DOCTOR)
        )
        lifecycleScope.launch() {
            val doctor = apiClient.doctorService.getDoctor()
            if (doctor.code == 200) {
                binding.tvName.text = doctor.data.birthday
            } else if (doctor.code in 400..499) {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.viewContain.apply {
            if (getCurrentHour() in 6..18) {
                setBackgroundResource(R.drawable.background_app_sun)
            } else {
                setBackgroundResource(R.drawable.background_app)
            }
        }
    }
}