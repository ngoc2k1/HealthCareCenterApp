package com.example.myapplication.patient

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
import com.example.myapplication.databinding.ActivityPatientProfileBinding
import com.example.myapplication.model.DoctorProfileRequest
import com.example.myapplication.model.PatientUpdateRequest
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant.AVT_FEMALE
import com.example.myapplication.utils.Constant.AVT_MALE
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.openCalendarDialog
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfilePatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPatientProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@ProfilePatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        val apiClient = ApiClient(this@ProfilePatientActivity)
        binding.apply {
            tvBirthday.setOnClickListener {
                openCalendarDialog(tvBirthday)
            }
            btnEdit.setOnClickListener {
                tvEmailNote.visible()
                tvPhoneNote.visible()
                edtHealthInsurance.isEnabled = true
                edtAddress.isEnabled = true
                edtIdentityCard.isEnabled = true
                tvBirthday.isEnabled = true
                inputFullName.isEnabled = true
                inputBlood.isEnabled = true
                inputWeight.isEnabled = true
                inputHeight.isEnabled = true
                rbMale.isEnabled = true
                rbFemale.isEnabled = true
                btnSave.visible()
                btnEdit.gone()
            }
            btnSave.setOnClickListener {
                tvEmailNote.gone()
                tvPhoneNote.gone()
                inputBlood.isEnabled = false
                inputWeight.isEnabled = false
                inputHeight.isEnabled = false
                edtHealthInsurance.isEnabled = false
                edtAddress.isEnabled = false
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
                    AVT_FEMALE
                else AVT_MALE
                val patientUpdate = PatientUpdateRequest(
                    edtAddress.text.toString(),
                    avatar,
                    tvBirthday.text.toString(),
                    inputBlood.text.toString(),
                    gender,
                    edtHealthInsurance.text.toString(),
                    inputHeight.text.toString().toInt(),
                    edtIdentityCard.text.toString(),
                    inputFullName.text.toString(),
                    inputWeight.text.toString().toDouble()
                )
                lifecycleScope.launch(Dispatchers.IO) {
                    val patientUpdateResponse = apiClient.patientService.updatePatient(
                        patientUpdate
                    )
                    withContext(Dispatchers.Main) {
                        if (patientUpdateResponse.isSuccessful()) {
                            patientUpdateResponse.data?.let {
                                toast(it.msg)
                                val mavatar = if (gender == GENDER.FEMALE.toString()) AVT_FEMALE
                                else AVT_MALE
                                Glide.with(imgAvatar).load(mavatar).centerCrop()
                                    .placeholder(R.drawable.img_default_avatar_home)
                                    .into(imgAvatar)
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                            }
                        } else {
                            toast(patientUpdateResponse.error?.error?.msg.toString())
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val apiClient = ApiClient(this@ProfilePatientActivity)
        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val patient = apiClient.patientService.getPatient()
                withContext(Dispatchers.Main) {
                    if (patient.isSuccessful()) {
                        patient.data?.data?.let {
                            it.apply {
                                edtHealthInsurance.setText(healthInsurance)
                                edtIdentityCard.setText(identityCard)
                                edtAddress.setText(address)
                                inputBlood.setText(bloodGroup)
                                inputHeight.setText(height.toString())
                                inputWeight.setText(weight.toString())
                                tvBirthday.text = birthday
                                inputPhone.setText(phone)
                                inputEmail.setText(email)
                                inputFullName.setText(name)
                                avatar = if (gender == GENDER.FEMALE.toString()) AVT_FEMALE
                                else AVT_MALE
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
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                        }
                    } else {
                        toast(patient.error?.error?.msg.toString())
                    }
                }
            }
        }
    }
}