package com.example.myapplication.patient

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
import com.example.myapplication.databinding.FragmentHomePatientBinding
import com.example.myapplication.doctor.ProfilePatientScheduleActivity
import com.example.myapplication.model.MedicalHistoryListDoctorResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePatientActivity : AppCompatActivity() {
    private lateinit var binding: FragmentHomePatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@HomePatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        binding.apply {
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
        val bundle = Bundle()
        binding.apply {
            if (getCurrentHour() in 6..18) {
                tvGreeting.text = getString(R.string.str_hello_user)
                tvGreeting.setTextColor(resources.getColor(R.color.black))
                tvFullName.setTextColor(resources.getColor(R.color.black))

                viewContain.setBackgroundResource(R.drawable.background_app_sun)
            } else {
                tvGreeting.text = getString(R.string.str_hello_user_evening)
                viewContain.setBackgroundResource(R.drawable.background_app)
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
                                tvBlood.text = bloodGroup
                                tvWeight.text = "$weight kg"
                                tvHeight.text = "$height cm"
//                        bundle.putString(Constant.ID_PATIENT, id.toString())
                                bundle.putString(Constant.HEIGHT, height.toString())
                                bundle.putString(Constant.WEIGHT, weight.toString())
                                bundle.putString(Constant.BLOOD, bloodGroup)
                                bundle.putString(Constant.AGE_PATIENT, age.toString())
                                bundle.putString(
                                    Constant.GENDER_PATIENT,
                                    convertGender(gender)
                                )
                                bundle.putString(Constant.NAME_PATIENT, name)
                                bundle.putString(Constant.AVT_PATIENT, avatar)
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
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
}