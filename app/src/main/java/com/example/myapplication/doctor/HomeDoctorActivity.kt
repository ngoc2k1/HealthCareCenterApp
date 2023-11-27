package com.example.myapplication.doctor

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorHomeBinding
import com.example.myapplication.utils.getCurrentHour
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class HomeDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorHomeBinding
    var context: Context? = null
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

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@HomeDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

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
                val options = ScanOptions()
                options.setPrompt(resources.getString(R.string.str_text_rq_guide))
                options.setBeepEnabled(true)
                options.setOrientationLocked(true)
                options.captureActivity = QRCodeDoctorActivity::class.java
                barLauncher.launch(options)
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

    private var barLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents != null) {
            val builder: android.app.AlertDialog.Builder =
                android.app.AlertDialog.Builder(context)
            builder.setTitle("Result")
            builder.setMessage(result.contents)
            builder.setPositiveButton(
                "OK"
            ) { dialogInterface, _ -> dialogInterface.dismiss() }
                .show()
        }
    }
}