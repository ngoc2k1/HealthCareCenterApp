package com.example.myapplication.doctor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorHomeBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class HomeDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorHomeBinding
    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

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