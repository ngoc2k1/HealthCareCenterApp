package com.example.myapplication.doctor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorWorkScheduleBinding

class ScheduleDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorWorkScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorWorkScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@ScheduleDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        binding.apply {
//            rcvWorkSchedule  -> fragment book schedule detail
//            ivInputDate.setOnClickListener{
//            }
//            tvInputDate.text=
        }
//        binding.tvNoneSchedule.visibility

    }
}