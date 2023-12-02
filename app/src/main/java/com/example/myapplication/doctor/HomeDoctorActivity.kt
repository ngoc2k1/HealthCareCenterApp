package com.example.myapplication.doctor

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorHomeBinding
import com.example.myapplication.model.doctor.DoctorAccountResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant.AVT_DOCTOR
import com.example.myapplication.utils.Constant.NAME_DOCTOR
import com.example.myapplication.utils.generateQR
import com.example.myapplication.utils.getCurrentHour
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorHomeBinding
    var context: Context? = null
    override fun onResume() {
        super.onResume()
        binding.apply {
            if (getCurrentHour() in 6..18) {
                tvGreeting.text = getString(R.string.str_hello_user)
                viewContain.setBackgroundResource(R.drawable.background_app_sun)
            } else {
                tvGreeting.text = getString(R.string.str_hello_user_evening)
                viewContain.setBackgroundResource(R.drawable.background_app)
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
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@HomeDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val bundle = Bundle()
        val apiClient = ApiClient(this@HomeDoctorActivity)
        context = this

        lifecycleScope.launch(Dispatchers.IO) {
            val doctor = apiClient.doctorService.getDoctor()
            if (doctor.code == 200) {
                withContext(Dispatchers.Main)
                {
                    binding.apply {
                        Glide.with(imgAvatar).load(doctor.data.avatar).centerCrop()
                            .placeholder(R.drawable.img_default_avatar_home)
                            .into(imgAvatar)
                        tvName.text = doctor.data.name
                    }
                    bundle.putString(NAME_DOCTOR, doctor.data.name)
                    bundle.putString(AVT_DOCTOR, doctor.data.avatar)
                }
            }
        }

        with(binding) {
            ivNotification.setOnClickListener {
                val intent = Intent(this@HomeDoctorActivity, NotificationDoctorActivity::class.java)
                startActivity(intent)
            }
            ivAccount.setOnClickListener {
                val intent = Intent(this@HomeDoctorActivity, AccountDoctorActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            rlQrCode.setOnClickListener {
                val options = ScanOptions()
                options.setPrompt(resources.getString(R.string.str_text_rq_guide))
                options.setBeepEnabled(true)
                options.setOrientationLocked(true)
                options.captureActivity = QRCodeDoctorActivity::class.java
                barLauncher.launch(options)
//                generateQR()
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