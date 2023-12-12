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
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.model.doctor.UserLoginRequest
import com.example.myapplication.model.doctor.UserLoginResponse
import com.example.myapplication.patient.HomePatientActivity
import com.example.myapplication.prefs.HawkKey
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.serviceapi.Resource
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.toast
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.security.auth.callback.Callback

class LoginPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@LoginPatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        context = this
        val apiClient = ApiClient(this@LoginPatientActivity)

        binding.apply {
            loginTvForgetPassword.setOnClickListener {
                val forgotPwDialog = ForgotPasswordPatientDialog()
                forgotPwDialog.isCancelable = false
                forgotPwDialog.mContext = this@LoginPatientActivity
                forgotPwDialog.show(supportFragmentManager, "dialog")//supportFM : activity
            }
            btnLogin.setOnClickListener {
                val username = loginEdtUsername.text.toString()
                val password = loginEdtPassword.text.toString()
                lifecycleScope.launch(Dispatchers.IO) {
                    val patient = apiClient.patientService.loginPatient(
                        UserLoginRequest(username, password)
                    )
                    withContext(Dispatchers.Main) {
                        if (patient.isSuccessful()) {
                            Hawk.put(HawkKey.ACCESS_TOKEN_PATIENT, patient.data?.data?.token)
                            startActivity(
                                Intent(
                                    this@LoginPatientActivity,
                                    HomePatientActivity::class.java
                                )
                            )
                        } else {
                            toast(patient.error?.error?.msg.toString())
                        }
                    }
                }
            }

            loginTvRegister.setOnClickListener {
                startActivity(Intent(this@LoginPatientActivity, RegisterActivity::class.java))
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
        binding.apply {
            val password = loginEdtPassword.text.toString()
            val username = loginEdtUsername.text.toString()

            if (password.isBlank() || username.isBlank()) {
                btnLogin.setBackgroundResource(R.drawable.bg_border_button_authen)
            } else btnLogin.setBackgroundResource(R.drawable.bg_border_button_authen_clicked)
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