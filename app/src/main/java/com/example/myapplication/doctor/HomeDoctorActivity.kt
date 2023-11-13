package com.example.myapplication.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorHomeBinding

class HomeDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.background_main)

        with(binding) {
//            tvName.text
//            imgAvatar.drawable
            ivNotification.setOnClickListener {
                val intent = Intent(this@HomeDoctorActivity, NotificationDoctorActivity::class.java)
                startActivity(intent)
            }
            ivAccount.setOnClickListener {
                val intent = Intent(this@HomeDoctorActivity, AccountDoctorActivity::class.java)
                startActivity(intent)
            }
            rlQrCode.setOnClickListener {
                val intent = Intent(this@HomeDoctorActivity, QRCodeDoctorActivity::class.java)
                startActivity(intent)
            }
            rlListPatient.setOnClickListener {
                val intent = Intent(this@HomeDoctorActivity, ListPatientActivity::class.java)
                startActivity(intent)
            }
            rlSchedule.setOnClickListener {
                val intent = Intent(this@HomeDoctorActivity, ScheduleDoctorActivity::class.java)
                startActivity(intent)
            }
        }
    }
}