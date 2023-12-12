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
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.Constant.AVT_DOCTOR
import com.example.myapplication.utils.Constant.NAME_DOCTOR
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.generateQR
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.toast
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
        val bundle = Bundle()
        val apiClient = ApiClient(this@HomeDoctorActivity)
        context = this
        binding.apply {
            if (getCurrentHour() in 6..18) {
                viewContain.setBackgroundResource(R.drawable.background_app_sun)
            } else {
                tvGreeting.text = getString(R.string.str_hello_user_evening)
                viewContain.setBackgroundResource(R.drawable.background_app)
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val doctor = apiClient.doctorService.getDoctor()
                withContext(Dispatchers.Main)
                {
                    if (doctor.isSuccessful()) {
                        doctor.data?.data?.let {
                            it.apply {
                                avatar = if (gender == GENDER.FEMALE.toString())
                                    Constant.AVT_FEMALE_DOCTOR
                                else Constant.AVT_MALE_DOCTOR
                                Glide.with(imgAvatar).load(avatar).centerCrop()
                                    .placeholder(R.drawable.img_default_avatar_home)
                                    .into(imgAvatar)
                                tvName.text = name
                                bundle.putString(NAME_DOCTOR, name)
                                bundle.putString(AVT_DOCTOR, avatar)
                            }
                        }
                    } else {
                        toast(doctor.error?.error?.msg.toString())
                    }
                }
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
            binding.apply {
                ivNotification.setOnClickListener {
                    val intent =
                        Intent(this@HomeDoctorActivity, NotificationDoctorActivity::class.java)
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
//                    generateQR()
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