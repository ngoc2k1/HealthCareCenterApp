package com.example.myapplication.doctor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMedicalHistoryDetailBinding
import com.example.myapplication.model.MedicalHistoryUpdateRequest
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant.ID_MEDICALHISTORY
import com.example.myapplication.utils.Constant.ID_PATIENT
import com.example.myapplication.utils.Constant.IS_PATIENT
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailMedicalHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicalHistoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@DetailMedicalHistoryActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val apiClient = ApiClient(this@DetailMedicalHistoryActivity)
        val idMedicalHistory = intent.getIntExtra(ID_MEDICALHISTORY, 0)
        val isPatient = intent.getIntExtra(IS_PATIENT, 0)
        binding.apply {
            if (isPatient == 1) {
                tvSave.gone()
                tvEdit.gone()
            } else {
                tvEdit.setOnClickListener {
                    edtJudgmentNote.isEnabled = true
                    edtPrescription.isEnabled = true
                    edtRetestDate.isEnabled = true
                    edtRetestDate.hint = "dd/mm/yyyy"
                    val retestDate = edtRetestDate.text.toString()
                    if (retestDate.isNotBlank())
                        if (retestDate.length > 10) edtRetestDate.setText(retestDate.substring(9))
                    edtTestResult.isEnabled = true
                    tvSave.visible()
                    tvEdit.gone()
                }
                tvSave.setOnClickListener {
                    edtRetestDate.hint = ""
                    lifecycleScope.launch(Dispatchers.IO) {
                        val updateMedicalHistory =
                            apiClient.doctorService.updateMedicalHistory(
                                idMedicalHistory,
                                MedicalHistoryUpdateRequest(
                                    edtJudgmentNote.text.toString(),
                                    edtPrescription.text.toString(),
                                    edtRetestDate.text.toString(),
                                    edtTestResult.text.toString()
                                )
                            )
                        withContext(Dispatchers.Main) {
                            if (updateMedicalHistory.isSuccessful()) {
                                toast(updateMedicalHistory.data?.msg.toString())
                            } else {
                                toast(updateMedicalHistory.error?.error?.msg.toString())
                            }
                        }
                    }
                    tvSave.gone()
                    tvEdit.visible()
                    edtJudgmentNote.isEnabled = false
                    edtPrescription.isEnabled = false
                    edtRetestDate.isEnabled = false
                    edtTestResult.isEnabled = false
                }
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val medicalHistory =
                    apiClient.patientService.getDetailMedicalHistory(idMedicalHistory)
                withContext(Dispatchers.Main) {
                    if (medicalHistory.isSuccessful()) {
                        medicalHistory.data?.data?.let {
                            it.apply {
                                if (judgmentNote.isNullOrBlank()) edtJudgmentNote.setText("")
                                else
                                    edtJudgmentNote.setText(judgmentNote)
                                if (prescription.isNullOrBlank()) edtPrescription.setText("")
                                else
                                    edtPrescription.setText(prescription)
                                if (testResult.isNullOrBlank()) edtTestResult.setText("")
                                else
                                    edtTestResult.setText(testResult)
                                if (retestDate.isNullOrBlank()) edtRetestDate.setText("")
                                else
                                    edtRetestDate.setText(retestDate)

                                bookSchedule.apply {
                                    tvDateTest.text = "Thời gian khám: $dateTest"
                                    if (namePatientTest.isNullOrBlank()) tvPatient.gone()
                                    else tvPatient.text = "Tên bệnh nhân: $namePatientTest"
                                    doctor.apply {
                                        tvAddress.text = "Địa chỉ khám: $addressTest"
                                        tvNameDoctor.text = name
                                        Glide.with(imgAvatar).load(avatar).centerCrop()
                                            .placeholder(R.drawable.img_default_avatar_home)
                                            .into(imgAvatar)
                                        specialty.apply {
                                            Glide.with(ivSpecialty).load(image).centerCrop()
                                                .placeholder(R.drawable.ic_hepatitis_transmission_black)
                                                .into(ivSpecialty)
                                            tvSpecialty.text = name
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        toast(medicalHistory.error?.error?.msg.toString())
                    }
                }
            }
        }
    }
}