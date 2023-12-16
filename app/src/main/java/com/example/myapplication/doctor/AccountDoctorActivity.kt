package com.example.myapplication.doctor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.auth.ChangePwDialog
import com.example.myapplication.auth.LoginActivity
import com.example.myapplication.databinding.ActivityDoctorAccountBinding
import com.example.myapplication.prefs.HawkKey
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.Constant.AVT_DOCTOR
import com.example.myapplication.utils.Constant.NAME_DOCTOR
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.toast
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorAccountBinding
    private lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@AccountDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        binding.apply {
            optionChangePassword.setOnClickListener {
                val changePwDialog = ChangePwDialog()
                changePwDialog.isCancelable = false
                changePwDialog.mContext = this@AccountDoctorActivity
                changePwDialog.show(supportFragmentManager, "dialog")//supportFM : activity
            }
            optionEditProfile.setOnClickListener {
                startActivity(Intent(this@AccountDoctorActivity, ProfileDoctorActivity::class.java))
            }
            optionChangeAccount.setOnClickListener {
                Hawk.put(HawkKey.ACCESS_TOKEN_DOCTOR, "")
                sharedpreferences = getSharedPreferences(Constant.SHARED_PREFS_DOCTOR, Context.MODE_PRIVATE)
                val editor = sharedpreferences.edit()
                editor.clear()
                editor.apply()
                startActivity(Intent(this@AccountDoctorActivity, LoginActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.viewContain.apply {
            if (getCurrentHour() in 6..18) {
                setBackgroundResource(R.drawable.background_app_sun)
            } else {
                setBackgroundResource(R.drawable.background_app)
            }
        }
        binding.apply {
            val apiClient = ApiClient(this@AccountDoctorActivity)
            lifecycleScope.launch(Dispatchers.IO) {
                val doctor = apiClient.doctorService.getDoctor()
                withContext(Dispatchers.Main) {
                    if (doctor.isSuccessful()) {
                        doctor.data?.data?.apply {
                            avatar = if (gender == GENDER.FEMALE.toString())
                                Constant.AVT_FEMALE_DOCTOR
                            else Constant.AVT_MALE_DOCTOR
                            Glide.with(imgAvatar).load(avatar).centerCrop()
                                .placeholder(R.drawable.img_default_avatar_home)
                                .into(imgAvatar)
                            tvName.text = name
                        }
                    } else {
                        toast(doctor.error?.error?.msg.toString())
                    }
                }
            }
        }
    }
}