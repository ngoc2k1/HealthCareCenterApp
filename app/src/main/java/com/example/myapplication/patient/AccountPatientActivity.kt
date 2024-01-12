package com.example.myapplication.patient

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.auth.ChangePwPatientDialog
import com.example.myapplication.auth.LoginActivity
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.prefs.HawkKey
import com.example.myapplication.prefs.Pref
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.Constant.SHARED_PREFS
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.toast
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountPatientActivity : AppCompatActivity() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@AccountPatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        binding.apply {
            optionChangePassword.setOnClickListener {
                val changePwDialog = ChangePwPatientDialog()
                changePwDialog.isCancelable = false
                changePwDialog.mContext = this@AccountPatientActivity
                changePwDialog.show(supportFragmentManager, "dialog")//supportFM : activity
            }

            optionChangeAccount.setOnClickListener {
                Hawk.put(HawkKey.ACCESS_TOKEN_PATIENT, "")
                val editor = sharedpreferences.edit()
                editor.clear()
                editor.apply()

                startActivity(Intent(this@AccountPatientActivity, LoginActivity::class.java))
            }
            optionEditProfile.setOnClickListener {
                startActivity(
                    Intent(
                        this@AccountPatientActivity,
                        ProfilePatientActivity::class.java
                    )
                )
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
            binding.apply {
                val apiClient = ApiClient(this@AccountPatientActivity)
                lifecycleScope.launch(Dispatchers.IO) {
                    val doctor = apiClient.patientService.getPatient()
                    withContext(Dispatchers.Main) {
                        if (doctor.isSuccessful()) {
                            doctor.data?.data?.let {
                                it.apply {
                                    avatar = if (gender == GENDER.FEMALE.toString())
                                        Constant.AVT_FEMALE
                                    else Constant.AVT_MALE

                                    Glide.with(imgAvatar).load(avatar).centerCrop()
                                        .placeholder(R.drawable.img_default_avatar_home)
                                        .into(imgAvatar)
                                    tvName.text = name
                                    tvBlood.text = bloodGroup
                                    tvHeight.text = height.toString()
                                    tvWeight.text = weight.toString()
                                }
                            }
                        } else {
                            toast(doctor.error?.error?.msg.toString())
                        }
                    }
                }
            }
        }
    }
}