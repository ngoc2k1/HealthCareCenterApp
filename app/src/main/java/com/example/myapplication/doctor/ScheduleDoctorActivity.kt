package com.example.myapplication.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorWorkScheduleBinding

class ScheduleDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorWorkScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorWorkScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor =
            this.resources.getColor(R.color.background_main)

        binding.apply {
//            rcvWorkSchedule
//            ivInputDate.setOnClickListener{
//            }
//            tvInputDate.text=
        }
//        binding.tvNoneSchedule.visibility

    }
}