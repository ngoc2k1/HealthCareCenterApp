package com.example.myapplication.auth

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.model.PatientRegisterRequest
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.openCalendarDialog
import com.example.myapplication.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@RegisterActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        val apiClient = ApiClient(this@RegisterActivity)

        binding.apply {
            tvLogin.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginPatientActivity::class.java))
            }
            btnRegister.setOnClickListener {
                val pw = inputPasswordRegister.text.toString()
                val confirmPW = inputConfirmPasswordRegister.text.toString()
                val address = edtAddress.text.toString()
                val dOB = tvBirthday.text.toString()
                val mail = inputEmailRegister.text.toString()
                val name = inputFullName.text.toString()
                val phone = inputPhoneRegister.text.toString()
                val gender = if (rbFemale.isChecked) GENDER.FEMALE.toString()
                else if (rbMale.isChecked) GENDER.MALE.toString()
                else ""
                if (TextUtils.isEmpty(pw) || TextUtils.isEmpty(confirmPW)
                    || TextUtils.isEmpty(address) || TextUtils.isEmpty(dOB)
                    || TextUtils.isEmpty(mail) || TextUtils.isEmpty(phone)
                    || TextUtils.isEmpty(name) || gender == ""
                ) {
                    toast(getString(R.string.str_error_change_pw))
                } else {
                    if (confirmPW != pw) {
                        toast(getString(R.string.str_error_change_pw_cf))
                    } else {
                        lifecycleScope.launch(Dispatchers.IO) {

                            val patientData = PatientRegisterRequest(
                                address,
                                dOB,
                                mail,
                                gender,
                                name,
                                pw,
                                phone
                            )
                            val patient = apiClient.patientService.registerPatient(
                                patientData
                            )

                            withContext(Dispatchers.Main) {
                                if (patient.isSuccessful()) {
                                    toast(patient.data?.msg.toString())
                                    startActivity(
                                        Intent(
                                            this@RegisterActivity,
                                            LoginPatientActivity::class.java
                                        )
                                    )
                                } else {
                                    toast(patient.error?.error?.msg.toString())
                                }
                            }
                        }
                    }
                }
            }
            rgGen.setOnCheckedChangeListener { buttonView, isChecked ->
                if (rgGen.checkedRadioButtonId != -1) {
                    checkInputRegister()
                } else btnRegister.setBackgroundResource(R.drawable.bg_border_button_authen)

            }

            tvBirthday.setOnClickListener {
                openCalendarDialog(tvBirthday)
            }

            edtAddress.addTextChangedListener(object : TextWatcher {
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
                        btnRegister.setBackgroundResource(R.drawable.bg_border_button_authen)
                    } else {
                        checkInputRegister()
                    }

                }
            })
            inputFullName.addTextChangedListener(object : TextWatcher {
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
                        btnRegister.setBackgroundResource(R.drawable.bg_border_button_authen)
                    } else {
                        checkInputRegister()
                    }

                }
            })
            inputConfirmPasswordRegister.addTextChangedListener(object : TextWatcher {
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
                        btnRegister.setBackgroundResource(R.drawable.bg_border_button_authen)
                    } else {
                        checkInputRegister()
                    }

                }
            })
            inputPhoneRegister.addTextChangedListener(object : TextWatcher {
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
                        btnRegister.setBackgroundResource(R.drawable.bg_border_button_authen)
                    } else {
                        checkInputRegister()
                    }

                }
            })
            inputPasswordRegister.addTextChangedListener(object : TextWatcher {
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
                        btnRegister.setBackgroundResource(R.drawable.bg_border_button_authen)
                    } else {
                        checkInputRegister()
                    }

                }
            })

        }
    }

    private fun checkInputRegister() {
        var isCheck = true

        binding.apply {
            val address = edtAddress.text.toString()
            val name = inputFullName.text.toString()
            val confirmPW = inputConfirmPasswordRegister.text.toString()
            val phone = inputPhoneRegister.text.toString()
            val pw = inputPasswordRegister.text.toString()
            val dOB = tvBirthday.text.toString()

            if (address.isBlank() || name.isBlank() || confirmPW.isBlank() || phone.isBlank() || pw.isBlank() || dOB.isBlank() || rgGen.checkedRadioButtonId == -1) {
                isCheck = false
            }

            if (isCheck) {
                btnRegister.setBackgroundResource(R.drawable.bg_border_button_authen_clicked)
            } else btnRegister.setBackgroundResource(R.drawable.bg_border_button_authen)
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