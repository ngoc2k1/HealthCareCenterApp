package com.example.myapplication.doctor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorAccountBinding
import com.example.myapplication.utils.getCurrentHour

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