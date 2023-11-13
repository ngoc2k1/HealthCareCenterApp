package com.example.myapplication.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorQrCodeBinding

class QRCodeDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorQrCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor =
            this.resources.getColor(R.color.background_main)
    }
}