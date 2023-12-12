package com.example.myapplication.doctor

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityDoctorListPatientBinding
import com.example.myapplication.model.PatientProfile
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant.ID_PATIENT
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.TimerTask

class ListPatientActivity : AppCompatActivity(), OnItemmClickListener {
    private lateinit var binding: ActivityDoctorListPatientBinding
    private var mListPatient: List<PatientProfile> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorListPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@ListPatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val apiClient = ApiClient(this@ListPatientActivity)
        val patientItemAdapter = ListPatientItemmAdapter(this@ListPatientActivity)
        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val listPatient = apiClient.doctorService.getListPatient("")
                withContext(Dispatchers.Main) {
                    if (listPatient.isSuccessful()) {
                        listPatient.data?.data?.let {
                            mListPatient = it
                        }
                        header.text = "Bệnh nhân (${listPatient.data?.data?.size})"
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                        rvMedicalHistory.adapter = patientItemAdapter
                        if (mListPatient.isEmpty()) {
                            tvNone.visible()
                            rvMedicalHistory.gone()
                        } else {
                            tvNone.gone()
                            rvMedicalHistory.visible()
                            patientItemAdapter.submitList(mListPatient)
                        }
                    } else {
                        toast(listPatient.error?.error?.msg.toString())
                    }
                }
            }
            edtSearchPatient.addTextChangedListener(object : TextWatcher {
                var timer: Timer? = Timer()
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    timer?.cancel()
                    timer = Timer()
                    timer?.schedule(
                        object : TimerTask() {
                            override fun run() {
                                runOnUiThread {
                                    lifecycleScope.launch(Dispatchers.IO) {
                                        val listPatient =
                                            apiClient.doctorService.getListPatient(s.toString())
                                        withContext(Dispatchers.Main)
                                        {
                                            if (listPatient.isLoading()) pbLoading.visible()

                                            if (listPatient.isSuccessful()) {
                                                pbLoading.gone()
                                                listPatient.data?.data?.let {
                                                    mListPatient = it
                                                    if (mListPatient.isEmpty()) {
                                                        tvNone.visible()
                                                        rvMedicalHistory.gone()
                                                    } else {
                                                        tvNone.gone()
                                                        rvMedicalHistory.visible()
                                                        patientItemAdapter.submitList(mListPatient)
                                                    }
                                                }
                                            } else {
                                                toast(listPatient.error?.error?.msg.toString())
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        1000
                    )
                }
            })
        }
    }

    override fun getDetailPatient(id: Int) {
        val intent = Intent(this@ListPatientActivity, ProfilePatientScheduleActivity::class.java)
        intent.putExtra(ID_PATIENT, id)
        startActivity(intent)
    }
}