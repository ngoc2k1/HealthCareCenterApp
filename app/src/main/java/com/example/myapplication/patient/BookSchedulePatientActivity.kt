package com.example.myapplication.patient

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.auth.LoginPatientActivity
import com.example.myapplication.databinding.FragmentBookScheduleBinding
import com.example.myapplication.doctor.ListPatientItemmAdapter
import com.example.myapplication.doctor.ProfilePatientScheduleActivity
import com.example.myapplication.model.DoctorBySpecialtyResponse
import com.example.myapplication.model.PatientProfile
import com.example.myapplication.model.Specialty
import com.example.myapplication.model.SpecialtyData
import com.example.myapplication.model.SpecialtyListResponse
import com.example.myapplication.prefs.HawkKey
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookSchedulePatientActivity : AppCompatActivity(), OnSpecialtyListener, OnDoctorListener {
    private lateinit var binding: FragmentBookScheduleBinding
    private var mListSpecialtyData = ArrayList<SpecialtyData>()
    private var mListDoctor: List<DoctorBySpecialtyResponse.Data> = emptyList()
    private lateinit var specialtyAdapter: SpecialtyListItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBookScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@BookSchedulePatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val apiClient = ApiClient(this@BookSchedulePatientActivity)
        specialtyAdapter = SpecialtyListItemAdapter(this@BookSchedulePatientActivity)
        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val listSpecialty = apiClient.patientService.getListSpecialty()
                withContext(Dispatchers.Main) {
                    if (listSpecialty.isSuccessful()) {
                        listSpecialty.data?.data?.let {
                            for (element in it) {
                                val photo = SpecialtyData(
                                    element.id, element.name, false
                                )
                                mListSpecialtyData.add(photo)
                            }
                            rvSpecialty.adapter = specialtyAdapter
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                            specialtyAdapter.submitList(mListSpecialtyData)
                        }
                    } else {
                        toast(listSpecialty.error?.error?.msg.toString())
                    }
                }
            }
        }
        callApiGetDoctorBySpecialty(1)
    }

    override fun getSpecialty(specialty: SpecialtyData) {
        mListSpecialtyData.forEach {
            it.isClicked = it.id == specialty.id
        }
        specialtyAdapter.submitList(mListSpecialtyData)
        toast("Chọn chuyên khoa " + specialty.name)
        callApiGetDoctorBySpecialty(specialty.id)
    }

    private fun callApiGetDoctorBySpecialty(id: Int) {
        val apiClient = ApiClient(this@BookSchedulePatientActivity)
        val doctorAdapter = DoctorListItemAdapter(this@BookSchedulePatientActivity)
        binding.apply {
            tvList.setOnClickListener {
                startActivity(
                    Intent(
                        this@BookSchedulePatientActivity,
                        ListBookSchedulePatientActivity::class.java
                    )
                )
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val listDoctor = apiClient.patientService.getDoctorBySpecialty(id,1)
                withContext(Dispatchers.Main) {
                    if (listDoctor.isSuccessful()) {
                        listDoctor.data?.data?.let {
                            it.apply {
                                mListDoctor = it
                                rvScheduleDoctor.adapter = doctorAdapter
                                doctorAdapter.submitList(mListDoctor)
                            }
                        }
                    }else {
                        toast(listDoctor.error?.error?.msg.toString())
                    }
                }
            }
        }
    }

    override fun getDoctor(id: Int) {
        val intent =
            Intent(
                this@BookSchedulePatientActivity,
                BookScheduleCreatePatientActivity::class.java
            )
        intent.putExtra(Constant.ID_DOCTOR, id)
        startActivity(intent)
    }
}