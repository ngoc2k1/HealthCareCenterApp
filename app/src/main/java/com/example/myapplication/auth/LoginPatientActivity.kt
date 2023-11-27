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
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.utils.getCurrentHour

class LoginPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@LoginPatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        binding.apply {
            loginTvForgetPassword.setOnClickListener {
                val forgotPwDialog = ForgotPwDialog()
                forgotPwDialog.show(supportFragmentManager, "dialog")//supportFM : activity
            }
            btnLogin.setOnClickListener {
                //call api
                startActivity(Intent(this@LoginPatientActivity, MainActivity::class.java))
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