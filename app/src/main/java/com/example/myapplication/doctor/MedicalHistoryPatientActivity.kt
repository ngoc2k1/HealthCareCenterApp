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
import com.example.myapplication.utils.Constant.ID_PATIENT
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.visible
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MedicalHistoryPatientActivity : AppCompatActivity(), OnMedicalHistoryClick {
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
            this@MedicalHistoryPatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val apiClient = ApiClient(this@MedicalHistoryPatientActivity)
        val idPatient = intent.getIntExtra(ID_PATIENT, 0)
        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val medicalHistoryResponse =
                    apiClient.doctorService.getListMedicalHistoryByDoctor(idPatient)
                if (medicalHistoryResponse.code == 200) {
                    mMedicalHistory = medicalHistoryResponse.data
                    if (mMedicalHistory.isEmpty()) {
                        tvNone.visible()
                        rvMedicalHistory.gone()
                    } else {
                        tvNone.gone()
                        rvMedicalHistory.visible()
                        withContext(Dispatchers.Main) {
                            val medicalHistoryItemAdapter =
                                MedicalHistoryListPatientItemmAdapter(this@MedicalHistoryPatientActivity)
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                            binding.rvMedicalHistory.adapter = medicalHistoryItemAdapter

                            medicalHistoryItemAdapter.submitList(mMedicalHistory)
                        }
                    }
                }
            }
//            lifecycleScope.launch(Dispatchers.IO) {
//                val patient =
//                    apiClient.patientService.getPatient()
//                if (patient.code == 200) {
//                    withContext(Dispatchers.Main) {
//                        binding.apply {
//                            patient.data.apply {
//                                Glide.with(imgAvatar).load(avatar)
//                                    .centerCrop()
//                                    .placeholder(R.drawable.img_default_avatar_home)
//                                    .into(imgAvatar)
//                                tvName.text = name
//                                tvBlood.text = bloodGroup
//                                tvWeight.text = "$weight kg"
//                                tvHeight.text = "$height cm"
//                                tvGenderAge.text = convertGender(gender) + ", " + age + " tuổi"
//                            }
//                        }
//                    }
//                }
//            }
        }
    }

    private var barLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents != null) {
            val builder: android.app.AlertDialog.Builder =
                android.app.AlertDialog.Builder(context)
            builder.setTitle("Result")
            builder.setMessage(result.contents)
            builder.setPositiveButton(
                "OK"
            ) { dialogInterface, _ -> dialogInterface.dismiss() }
                .show()
        }
    }

    override fun getDetailMedicalHistory(id: Int) {
//            val intent = Intent(this@MedicalHistoryPatientActivity, DetailScheduleActivity::class.java)
//            intent.putExtra(Constant.ID_MEDICALHISTORY, id)
//            startActivity(intent)
    }
}