package com.example.myapplication.patient

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
import com.example.myapplication.doctor.DetailMedicalHistoryActivity
import com.example.myapplication.doctor.OnMedicalHistoryClick
import com.example.myapplication.model.MedicalHistoryListDoctorResponse
import com.example.myapplication.model.MedicalHistoryListPatientResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.Constant.ID_PATIENT
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfilePatientScheduleByPatientActivity : AppCompatActivity(), OnMedicalHistoryClick {
    private lateinit var binding: FragmentProfilePatientBinding
    var context: Context? = null
    private var mMedicalHistory: List<MedicalHistoryListPatientResponse.Data> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfilePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@ProfilePatientScheduleByPatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        val bundle: Bundle? = intent.extras
        val apiClient = ApiClient(this@ProfilePatientScheduleByPatientActivity)
        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val medicalHistoryResponse =
                    apiClient.patientService.getListMedicalHistoryByPatient(1)
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
                                    ProfilePatienScheduleItemFullAdapter(this@ProfilePatientScheduleByPatientActivity)
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                                rvMedicalHistory.adapter = medicalHistoryItemAdapter
                                medicalHistoryItemAdapter.submitList(mMedicalHistory)
                            }
                        }
                    }else {
                        toast(medicalHistoryResponse.error?.error?.msg.toString())
                    }
                }
            }
            if (bundle != null) {
                val avatar = bundle.getString(Constant.AVT_PATIENT)
                Glide.with(imgAvatar).load(avatar)
                    .centerCrop()
                    .placeholder(R.drawable.img_default_avatar_home)
                    .into(imgAvatar)
                tvName.text = bundle.getString(Constant.NAME_PATIENT)
                tvBlood.text = bundle.getString(Constant.BLOOD)
                tvWeight.text = bundle.getString(Constant.WEIGHT) + " kg"
                tvHeight.text = bundle.getString(Constant.HEIGHT) + " cm"
                tvGenderAge.text =
                    bundle.getString(Constant.GENDER_PATIENT) + ", " + bundle.getString(Constant.AGE_PATIENT) + " tuổi"
            }
        }

    }

    override fun getDetailMedicalHistory(id: Int) {
        val intent =
            Intent(
                this@ProfilePatientScheduleByPatientActivity,
                DetailMedicalHistoryActivity::class.java
            )
        intent.putExtra(Constant.ID_MEDICALHISTORY, id)
        intent.putExtra(Constant.IS_PATIENT, 1)
        startActivity(intent)
    }
}