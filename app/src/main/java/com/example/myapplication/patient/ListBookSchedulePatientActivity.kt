package com.example.myapplication.patient

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityPatinetListBookBinding
import com.example.myapplication.doctor.OnMedicalHistoryClick
import com.example.myapplication.model.BookScheduleByPatientResponse
import com.example.myapplication.model.PatientProfile
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.Constant.ID_PATIENT
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.TimerTask

class ListBookSchedulePatientActivity : AppCompatActivity(), OnDetailSchedule {
    private lateinit var binding: ActivityPatinetListBookBinding
    private var mBookSchedule: List<BookScheduleByPatientResponse.Data> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatinetListBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@ListBookSchedulePatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onResume() {
        super.onResume()
        val apiClient = ApiClient(this@ListBookSchedulePatientActivity)
        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val medicalHistoryResponse =
                    apiClient.patientService.getListBookScheduleByPatient()
                withContext(Dispatchers.Main) {
                    if (medicalHistoryResponse.isLoading()) pbLoading.visible()
                    else if (medicalHistoryResponse.isSuccessful()) {
                        pbLoading.gone()
                        rvMedicalHistory.visible()
                        medicalHistoryResponse.data?.data?.let {
                            it.apply {
                                mBookSchedule = it
                                if (mBookSchedule.isNullOrEmpty()) {
                                    tvNone.visible()
                                    rvMedicalHistory.gone()
                                } else {
                                    tvNone.gone()
                                    rvMedicalHistory.visible()
                                    val mAdapter =
                                        ListBookSchedulePatientAdapter(this@ListBookSchedulePatientActivity)
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                                    rvMedicalHistory.adapter = mAdapter
                                    mAdapter.submitList(mBookSchedule)
                                }
                            }
                        }
                    } else {
                        toast(medicalHistoryResponse.error?.error?.msg.toString())
                    }
                }
            }
        }
    }

    override fun getDetailSchedule(id: Int) {
        val intent = Intent(
            this@ListBookSchedulePatientActivity,
            DetailSchedulePatientActivity::class.java
        )
        intent.putExtra(Constant.ID_BOOKSCHEDULE, id)
        startActivity(intent)
    }

}