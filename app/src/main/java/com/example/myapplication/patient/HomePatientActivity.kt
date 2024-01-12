package com.example.myapplication.patient

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomePatientBinding
import com.example.myapplication.prefs.HawkKey
import com.example.myapplication.prefs.Pref
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.user.ChatActivity
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.toast
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePatientActivity : AppCompatActivity() {
    private lateinit var binding: FragmentHomePatientBinding
    val bundleChat = Bundle()
    var namePatient = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        capQuyenThongBao()
        binding = FragmentHomePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@HomePatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        binding.apply {

            ivChat.setOnClickListener {
                val intent =
                    Intent(this@HomePatientActivity, ChatActivity::class.java)
                intent.putExtras(bundleChat)
                startActivity(intent)
            }
            tvBookSchedule.setOnClickListener {
                val intent =
                    Intent(this@HomePatientActivity, BookSchedulePatientActivity::class.java)
                startActivity(intent)
            }
            tvAccount.setOnClickListener {
                val intent =
                    Intent(this@HomePatientActivity, AccountPatientActivity::class.java)
                startActivity(intent)
            }
            tvNoti.setOnClickListener {
                val intent =
                    Intent(this@HomePatientActivity, NotificationPatientActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        val apiClient = ApiClient(this@HomePatientActivity)
        val bundleMedicalRecords = Bundle()
        binding.apply {
            if (getCurrentHour() in 6..18) {
                tvGreeting.text = getString(R.string.str_hello_user)
                tvGreeting.setTextColor(resources.getColor(R.color.black))
                tvChat.setTextColor(resources.getColor(R.color.black))
                tvFullName.setTextColor(resources.getColor(R.color.black))

                viewContain.setBackgroundResource(R.drawable.background_app_sun)
                ivChat.setBackgroundResource(R.drawable.ic_chat)
            } else {
                tvGreeting.text = getString(R.string.str_hello_user_evening)
                ivChat.setBackgroundResource(R.drawable.ic_chat_white)
                viewContain.setBackgroundResource(R.drawable.background_app)
                tvChat.setTextColor(resources.getColor(R.color.white))
                tvGreeting.setTextColor(resources.getColor(R.color.white))
                tvFullName.setTextColor(resources.getColor(R.color.white))
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val patient = apiClient.patientService.getPatient()
                withContext(Dispatchers.Main) {
                    if (patient.isSuccessful()) {
                        patient.data?.data?.let {
                            it.apply {
                                avatar =
                                    if (gender == GENDER.FEMALE.toString())
                                        Constant.AVT_FEMALE
                                    else Constant.AVT_MALE
                                Glide.with(imgAvatar).load(avatar).centerCrop()
                                    .placeholder(R.drawable.img_default_avatar_home)
                                    .into(imgAvatar)
                                tvFullName.text = name
                                namePatient = name
                                tvBlood.text = bloodGroup
                                tvWeight.text = "$weight kg"
                                tvHeight.text = "$height cm"
//                        bundleMedicalRecords.putString(Constant.ID_PATIENT, id.toString())
                                bundleMedicalRecords.putString(Constant.HEIGHT, height.toString())
                                Pref.setString(this@HomePatientActivity, Constant.NAME_PATIENT_FB, name)

                                bundleMedicalRecords.putString(Constant.WEIGHT, weight.toString())
                                bundleMedicalRecords.putString(Constant.BLOOD, bloodGroup)
                                bundleMedicalRecords.putString(Constant.AGE_PATIENT, age.toString())
                                bundleMedicalRecords.putString(
                                    Constant.GENDER_PATIENT,
                                    convertGender(gender)
                                )
                                bundleMedicalRecords.putString(Constant.NAME_PATIENT, name)
                                bundleMedicalRecords.putString(Constant.AVT_PATIENT, avatar)
                                val token = Hawk.get<String>(HawkKey.ACCESS_TOKEN_PATIENT)
                                bundleChat.putString(Constant.CHAT, "$token*$name")
                            }
                        }
                    } else {
                        toast(patient.error?.error?.msg.toString())
                    }
                }
            }
            tvMedicalRecords.setOnClickListener {
                val intent =
                    Intent(
                        this@HomePatientActivity,
                        ProfilePatientScheduleByPatientActivity::class.java
                    )
                intent.putExtras(bundleMedicalRecords)
                startActivity(intent)
            }
        }
    }

    private fun capQuyenThongBao() {
        val p3 =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
        if (p3 != PackageManager.PERMISSION_GRANTED) {
//sdk 33 trở đi cần xin quyền gửi thông báo
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.POST_NOTIFICATIONS
                ), 123
            )
        }
    }
}