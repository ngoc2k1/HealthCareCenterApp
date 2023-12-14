package com.example.myapplication.patient

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.BookScheduleCreateBinding
import com.example.myapplication.model.BookCreatedRequest
import com.example.myapplication.model.DateByDoctorUI
import com.example.myapplication.model.TimeByDoctorUI
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant.ID_DOCTOR
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BookScheduleCreatePatientActivity : AppCompatActivity(), OnTimeListener, OnDateListener {
    private lateinit var binding: BookScheduleCreateBinding
    private var mDate = ArrayList<DateByDoctorUI>()
    private var mTime = ArrayList<TimeByDoctorUI>()
    private lateinit var timeListItemAdapter: TimeListItemAdapter
    private lateinit var dateListAdapter: DateListItemAdapter
    private var idDoctor = 0

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
        idDoctor = intent.getIntExtra(ID_DOCTOR, 0)
        val apiClient = ApiClient(this@BookScheduleCreatePatientActivity)
        dateListAdapter = DateListItemAdapter(this@BookScheduleCreatePatientActivity)
        timeListItemAdapter = TimeListItemAdapter(this@BookScheduleCreatePatientActivity)
        binding.apply {
            ivHome.setOnClickListener {
                val intent = Intent(
                    this@BookScheduleCreatePatientActivity,
                    HomePatientActivity::class.java
                )
                startActivity(intent)
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val patient = apiClient.patientService.getPatient()
                withContext(Dispatchers.Main) {
                    if (patient.isSuccessful()) {
                        patient.data?.data?.let {
                            it.apply {
                                informationQR =
                                    "Tên người đặt lịch: $name. "
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
                                for (item: String in it) {
                                    mDate.add(DateByDoctorUI(item, false))
                                }
                                val gridLayoutManager = GridLayoutManager(
                                    applicationContext, 2
                                )
                                gridLayoutManager.orientation =
                                    LinearLayoutManager.VERTICAL
                                rvDate.layoutManager = gridLayoutManager
                                rvDate.adapter = dateListAdapter
                                dateListAdapter.submitList(mDate)
                            }
                        }
                    } else {
                        toast(listDate.error?.error?.msg.toString())
                    }
                }
            }
            tvSave.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val namePatientTest = edtNamePatientTest.text.trim().toString()
                    val statusHeath = edtStatus.text.trim().toString()
                    var qrcode = informationQR
                    if (namePatientTest.isNotBlank()) {
                        qrcode += "Tên người khám: $namePatientTest. "
                    }
                    if (edtStatus.text.toString().isNotBlank()) {
                        qrcode += "Tình trạng sức khỏe: $statusHeath."
                    }
                    val response = apiClient.patientService.createBookSchedule(
                        BookCreatedRequest(
                            mdoctorWorkScheduleId,
                            namePatientTest,
                            qrcode,
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

    override fun getTime(doctorWorkScheduleId: Int, time: TimeByDoctorUI) {
        mTime.forEach {
            it.isClicked = it.value == time.value
        }
        mdoctorWorkScheduleId = doctorWorkScheduleId
        val gridLayoutManager = GridLayoutManager(
            applicationContext, 2
        )
        gridLayoutManager.orientation =
            LinearLayoutManager.VERTICAL
        binding.rvTime.layoutManager = gridLayoutManager
        binding.rvTime.adapter = timeListItemAdapter
        timeListItemAdapter.submitList(mTime)
    }

    override fun getDate(date: DateByDoctorUI) {
        val apiClient = ApiClient(this@BookScheduleCreatePatientActivity)
        val timeListAdapter = TimeListItemAdapter(this@BookScheduleCreatePatientActivity)
        mDate.forEach {
            it.isClicked = it.date == date.date
        }
        val gridLayoutManager = GridLayoutManager(
            applicationContext, 2
        )
        gridLayoutManager.orientation =
            LinearLayoutManager.VERTICAL
        binding.rvDate.layoutManager = gridLayoutManager
        binding.rvDate.adapter = dateListAdapter
        dateListAdapter.submitList(mDate)
        mTime.clear()
        lifecycleScope.launch(Dispatchers.IO) {
            val listDoctor =
                apiClient.patientService.getTimeByDoctor(
                    idDoctor,
                    date.date
                )
            withContext(Dispatchers.Main) {
                if (listDoctor.isSuccessful()) {
                    listDoctor.data?.data?.let {
                        var countBooked = 0
                        for (element in it) {
                            if (!element.isBooked) {
                                val data = TimeByDoctorUI(
                                    element.id,
                                    element.value,
                                    false,
                                    isBooked = false,
                                    price = element.price
                                )
                                mTime.add(data)
                            } else countBooked++
                        }
                        if (countBooked == it.size) {
                            binding.tvNone.visible()
                            binding.rvTime.gone()
                        } else {
                            binding.tvNone.gone()
                            binding.rvTime.visible()
                            val gridLayoutManager = GridLayoutManager(
                                applicationContext, 2
                            )
                            gridLayoutManager.orientation =
                                LinearLayoutManager.VERTICAL
                            binding.rvTime.layoutManager = gridLayoutManager
                            binding.rvTime.adapter = timeListAdapter
                            timeListAdapter.submitList(mTime)

                        }
                    }
                } else {
                    toast(listDoctor.error?.error?.msg.toString())
                }
            }
        }
    }
}