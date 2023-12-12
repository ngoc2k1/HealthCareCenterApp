package com.example.myapplication.patient

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.BookScheduleCreateBinding
import com.example.myapplication.model.BookCreatedRequest
import com.example.myapplication.model.TimeByDoctorResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.Constant.ID_DOCTOR
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BookScheduleCreatePatientActivity : AppCompatActivity(), OnTimeListener {
    private lateinit var binding: BookScheduleCreateBinding
    private var mDate: List<String> = emptyList()
    private var mTime: List<TimeByDoctorResponse.Data> = emptyList()
    var mDateClicked = ""
    var mdoctorWorkScheduleId = 0
    var informationQR = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookScheduleCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@BookScheduleCreatePatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        val idDoctor = intent.getIntExtra(ID_DOCTOR, 0)
        val apiClient = ApiClient(this@BookScheduleCreatePatientActivity)
        val timeListAdapter = TimeListItemAdapter(this@BookScheduleCreatePatientActivity)


        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val patient = apiClient.patientService.getPatient()
                withContext(Dispatchers.Main) {
                    if (patient.isSuccessful()) {
                        patient.data?.data?.let {
                            it.apply {
                                informationQR =
                                    "Tên người đặt lịch: $name | ${convertGender(gender)}\n"
                            }
                        }
                    } else {
                        toast(patient.error?.error?.msg.toString())
                    }
                }
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val listDate = apiClient.patientService.getDateByDoctor(idDoctor)
                withContext(Dispatchers.Main) {
                    if (listDate.isSuccessful()) {
                        listDate.data?.data?.let {
                            it.apply {
                                mDate = it
                                val myArrayAdapter = ArrayAdapter(
                                    this@BookScheduleCreatePatientActivity,
                                    com.bumptech.glide.R.layout.support_simple_spinner_dropdown_item,
                                    mDate
                                )
                                rvDate.adapter = myArrayAdapter
                                rvDate.onItemClickListener =
                                    AdapterView.OnItemClickListener { _, _, i, _ ->
                                        try {
                                            for (ctr in 0..mDate.size) {
                                                if (i == ctr) {
                                                    rvDate.getChildAt(ctr)
                                                        .setBackgroundResource(R.drawable.bg_item_selected)
                                                    mDateClicked = mDate[ctr]
                                                    lifecycleScope.launch(Dispatchers.IO) {
                                                        val listDoctor =
                                                            apiClient.patientService.getTimeByDoctor(
                                                                idDoctor,
                                                                mDateClicked
                                                            )
                                                        withContext(Dispatchers.Main) {
                                                            if (listDoctor.isSuccessful()) {
                                                                listDoctor.data?.data?.let {
                                                                    mTime = it
                                                                    rvTime.adapter = timeListAdapter
                                                                    timeListAdapter.submitList(mTime)
                                                                }
                                                            } else {
                                                                toast(listDoctor.error?.error?.msg.toString())
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    rvDate.getChildAt(ctr)
                                                        .setBackgroundResource(R.drawable.bg_item_unselected)
                                                }
                                            }
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }
                            }
//                        rvDate.onItemClickListener =
//                            AdapterView.OnItemClickListener { parent, view, position, id ->
//                                mDateClicked = mDate[position]
//                                view.setBackgroundResource(com.example.myapplication.R.drawable.bg_item_selected)
//                                lifecycleScope.launch(Dispatchers.IO) {
//                                    val listDoctor =
//                                        apiClient.patientService.getTimeByDoctor(
//                                            idDoctor,
//                                            mDateClicked
//                                        )
//                                    if (listDoctor.code == 200) {
//                                        mTime = listDoctor.data
//                                        withContext(Dispatchers.Main) {
//                                            rvTime.adapter = timeListAdapter
////        binding.pbMainLoadingvideo.visibility = View.VISIBLE
//                                            timeListAdapter.submitList(mTime)
//                                        }
//                                    }
//                                }
//                            }
                        }
                    } else {
                        toast(listDate.error?.error?.msg.toString())
                    }
                }
            }
            tvSave.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val namePatientTest = edtNamePatientTest.text.toString()
                    val statusHeath = edtStatus.text.toString()
                    val response = apiClient.patientService.createBookSchedule(
                        BookCreatedRequest(
                            mdoctorWorkScheduleId,
                            namePatientTest,
                            informationQR + "Tên người khám: $namePatientTest\nTình trạng sức khỏe: $statusHeath",
                            statusHeath
                        )
                    )
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful()) {
                            toast(response.data?.msg.toString())
                            startActivity(
                                Intent(
                                    this@BookScheduleCreatePatientActivity,
                                    ListBookSchedulePatientActivity::class.java
                                )
                            )
                        } else {
                            toast(response.error?.error?.msg.toString())
                        }
                    }
                }
            }

        }
    }

    override fun getTime(doctorWorkScheduleId: Int) {
        mdoctorWorkScheduleId = doctorWorkScheduleId
    }
}