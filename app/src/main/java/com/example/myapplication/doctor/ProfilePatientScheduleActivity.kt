package com.example.myapplication.doctor

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
import com.example.myapplication.databinding.FragmentProfilePatientBinding
import com.example.myapplication.model.MedicalHistoryListDoctorResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.Constant.AVT_FEMALE
import com.example.myapplication.utils.Constant.AVT_MALE
import com.example.myapplication.utils.Constant.ID_PATIENT
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfilePatientScheduleActivity : AppCompatActivity(), OnMedicalHistoryClick {
    private lateinit var binding: FragmentProfilePatientBinding
    var context: Context? = null
    private var mMedicalHistory: List<MedicalHistoryListDoctorResponse.Data> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfilePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@ProfilePatientScheduleActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val apiClient = ApiClient(this@ProfilePatientScheduleActivity)
        val idPatient = intent.getIntExtra(ID_PATIENT, 0)
        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val medicalHistoryResponse =
                    apiClient.doctorService.getListMedicalHistoryByDoctor(idPatient,1)
                withContext(Dispatchers.Main) {
                    if (medicalHistoryResponse.isSuccessful()) {
                        medicalHistoryResponse.data?.data?.let {
                            mMedicalHistory = it
                            if (mMedicalHistory.isNullOrEmpty()) {
                                tvNone.visible()
                                rvMedicalHistory.gone()
                            } else {
                                tvNone.gone()
                                rvMedicalHistory.visible()
                                val medicalHistoryItemAdapter =
                                    MedicalHistoryListPatientItemmAdapter(this@ProfilePatientScheduleActivity)
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                                rvMedicalHistory.adapter = medicalHistoryItemAdapter
                                medicalHistoryItemAdapter.submitList(mMedicalHistory)
                            }
                        }
                    } else {
                        toast(medicalHistoryResponse.error?.error?.msg.toString())
                    }
                }
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val patient =
                    apiClient.doctorService.getPatientByDoctor(idPatient)
                withContext(Dispatchers.Main) {
                    if (patient.isSuccessful()) {
                        patient.data?.data?.let {
                            it.apply {
                                avatar = if (gender == GENDER.FEMALE.toString())
                                    AVT_FEMALE
                                else AVT_MALE
                                Glide.with(imgAvatar).load(avatar)
                                    .centerCrop()
                                    .placeholder(R.drawable.img_default_avatar_home)
                                    .into(imgAvatar)
                                tvName.text = name
                                tvBlood.text = bloodGroup
                                tvWeight.text = "$weight kg"
                                tvHeight.text = "$height cm"
                                tvGenderAge.text = convertGender(gender) + ", " + age + " tuổi"
                            }
                        }
                    } else {
                        toast(patient.error?.error?.msg.toString())
                    }
                }
            }
        }
    }

    override fun getDetailMedicalHistory(id: Int) {
        val intent =
            Intent(this@ProfilePatientScheduleActivity, DetailMedicalHistoryActivity::class.java)
        intent.putExtra(Constant.ID_MEDICALHISTORY, id)
        startActivity(intent)
    }
}