package com.example.myapplication.doctor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorProfileBinding
import com.example.myapplication.model.DoctorProfileRequest
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
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
                val avatar = if (gender == GENDER.FEMALE.toString())
                    Constant.AVT_FEMALE_DOCTOR
                else Constant.AVT_MALE_DOCTOR
                val doctorUpdate = DoctorProfileRequest(
                    edtAddress.text.toString(),
                    avatar,
                    tvBirthday.text.toString(),
                    gender,
                    edtHealthInsurance.text.toString(),
                    edtIdentityCard.text.toString(),
                    inputFullName.text.toString()
                )
                lifecycleScope.launch(Dispatchers.IO) {
                    val doctorUpdateResponse = apiClient.doctorService.updateDoctor(
                        doctorUpdate
                    )
                    withContext(Dispatchers.Main) {
                        if (doctorUpdateResponse.isSuccessful()) {
                            doctorUpdateResponse.data?.let {
                                toast(it.msg)
                                val mavatar =
                                    if (gender == GENDER.FEMALE.toString()) Constant.AVT_FEMALE_DOCTOR
                                    else Constant.AVT_MALE_DOCTOR
                                Glide.with(imgAvatar).load(mavatar).centerCrop()
                                    .placeholder(R.drawable.img_default_avatar_home)
                                    .into(imgAvatar)
                            }
                        } else {
                            toast(doctorUpdateResponse.error?.error?.msg.toString())
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val apiClient = ApiClient(this@ProfileDoctorActivity)
        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val doctor = apiClient.doctorService.getDoctor()
                withContext(Dispatchers.Main)
                {
                    if (doctor.isSuccessful()) {
                        doctor.data?.data?.let {
                            it.apply {
                                edtAddress.setText(addressTest)
                                edtHealthInsurance.setText(healthInsurance)
                                edtIdentityCard.setText(identityCard)
                                tvBirthday.text = birthday
                                inputPhone.setText(phone)
                                inputEmail.setText(email)
                                inputFullName.setText(name)
                                avatar = if (gender == GENDER.FEMALE.toString())
                                    Constant.AVT_FEMALE_DOCTOR
                                else Constant.AVT_MALE_DOCTOR
                                Glide.with(imgAvatar).load(avatar).centerCrop()
                                    .placeholder(R.drawable.img_default_avatar_home)
                                    .into(imgAvatar);

                                if (gender == GENDER.MALE.toString()) {
                                    rbMale.isChecked = true
                                    rbFemale.isChecked = false
                                } else {
                                    rbFemale.isChecked = true
                                    rbMale.isChecked = false
                                }
                            }
                        }
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                    } else {
                        toast(doctor.error?.error?.msg.toString())
                    }
                }
            }
        }
    }
}