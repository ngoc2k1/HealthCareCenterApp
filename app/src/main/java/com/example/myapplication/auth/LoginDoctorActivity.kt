package com.example.myapplication.auth

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginDoctorBinding
import com.example.myapplication.doctor.AccountDoctorActivity
import com.example.myapplication.doctor.HomeDoctorActivity
import com.example.myapplication.doctor.NotificationDoctorActivity
import com.example.myapplication.model.doctor.DoctorLoginRequest
import com.example.myapplication.prefs.HawkKey
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.toast
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginDoctorBinding
    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var apiClient = ApiClient(this@LoginDoctorActivity)

        context = this

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@LoginDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        binding.apply {
            loginTvForgetPassword.setOnClickListener {
                val forgotPwDialog = ForgotPwDialog()
                forgotPwDialog.show(supportFragmentManager, "dialog")//supportFM : activity
            }
            btnLogin.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val doctor = apiClient.doctorService.loginDoctor(
                        DoctorLoginRequest(
                            "email_2@gmail.com", "12345"
                        )
                    )
                    if (doctor.code == 200) {
                        withContext(Dispatchers.Main) {
                            toast(
                                Hawk.get<String>(HawkKey.ACCESS_TOKEN_DOCTOR)
                            )
                        }
                        startActivity(
                            Intent(
                                this@LoginDoctorActivity,
                                AccountDoctorActivity::class.java
                            )
                        )
                    } else if (doctor.code in 400..499) {
                        withContext(Dispatchers.Main) {
                            btnLogin.text = doctor.data.token
                        }
                    }
                }
            }

            loginEdtUsername.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (s.isNullOrEmpty()) {
                        btnLogin.setBackgroundResource(R.drawable.bg_border_button_authen)
                    } else {
                        checkInputLogin()
                    }

                }
            })
            loginEdtPassword.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (s.isNullOrEmpty()) {
                        btnLogin.setBackgroundResource(R.drawable.bg_border_button_authen)
                    } else {
                        checkInputLogin()
                    }

                }
            })
        }
    }

    fun checkInputLogin() {
        var isCheck = true

        binding.apply {
            val password = loginEdtPassword.text.toString()
            val username = loginEdtUsername.text.toString()

            if (password.isBlank() || username.isBlank()) {
                isCheck = false
            }

            if (isCheck) {
                btnLogin.setBackgroundResource(R.drawable.bg_border_button_authen_clicked)
            } else btnLogin.setBackgroundResource(R.drawable.bg_border_button_authen)
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
    }
}