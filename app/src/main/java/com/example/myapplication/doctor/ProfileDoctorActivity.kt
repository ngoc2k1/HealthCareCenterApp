package com.example.myapplication.doctor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorProfileBinding
import com.example.myapplication.model.DoctorProfileRequest
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.openCalendarDialog
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@ProfileDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val apiClient = ApiClient(this@ProfileDoctorActivity)
        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val doctor = apiClient.doctorService.getDoctor()
                if (doctor.code == 200) {
                    withContext(Dispatchers.Main)
                    {
                        doctor.data.apply {
                            edtAddress.setText(addressTest)
                            edtHealthInsurance.setText(healthInsurance)
                            edtIdentityCard.setText(identityCard)
                            tvBirthday.text = birthday
                            inputPhone.setText(phone)
                            inputEmail.setText(email)
                            inputFullName.setText(name)
                            if (gender == GENDER.MALE.toString()) {
                                rbMale.isChecked = true
                                rbFemale.isChecked = false
                            } else {
                                rbFemale.isChecked = true
                                rbMale.isChecked = false
                            }
                        }
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                    }
                }
            }
            tvBirthday.setOnClickListener {
                openCalendarDialog(tvBirthday)
            }
            btnEdit.setOnClickListener {
                tvEmailNote.visible()
                tvPhoneNote.visible()
                edtAddress.isEnabled = true
                edtHealthInsurance.isEnabled = true
                edtIdentityCard.isEnabled = true
                tvBirthday.isEnabled = true
                inputFullName.isEnabled = true
                rbMale.isEnabled = true
                rbFemale.isEnabled = true
                btnSave.visible()
                btnEdit.gone()
            }
            btnSave.setOnClickListener {
                tvEmailNote.gone()
                tvPhoneNote.gone()
                edtAddress.isEnabled = false
                edtHealthInsurance.isEnabled = false
                edtIdentityCard.isEnabled = false
                tvBirthday.isEnabled = false
                inputFullName.isEnabled = false
                rbMale.isEnabled = false
                rbFemale.isEnabled = false
                btnSave.gone()
                btnEdit.visible()
                val gender = if (rbFemale.isChecked) GENDER.FEMALE.toString()
                else GENDER.MALE.toString()
                lifecycleScope.launch(Dispatchers.IO) {
                    val doctorUpdateResponse = apiClient.doctorService.updateDoctor(
                        DoctorProfileRequest(
                            edtAddress.text.toString(),
                            "",
                            tvBirthday.text.toString(),
                            gender,
                            edtHealthInsurance.text.toString(),
                            edtIdentityCard.text.toString(),
                            inputFullName.text.toString()
                        )
                    )
                    if (doctorUpdateResponse.code == 200) {
                        withContext(Dispatchers.Main) {
                            toast(doctorUpdateResponse.msg)
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            if (getCurrentHour() in 6..18) {
                viewContain.setBackgroundResource(R.drawable.background_app_sun)
                tvEmailNote.setTextColor(resources.getColor(R.color.black))
                tvPhoneNote.setTextColor(resources.getColor(R.color.black))
            } else {
                viewContain.setBackgroundResource(R.drawable.background_app)
                tvEmailNote.setTextColor(resources.getColor(R.color.grey))
                tvPhoneNote.setTextColor(resources.getColor(R.color.grey))
            }
        }
    }
}