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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var currentPage = 1
    private lateinit var listLayoutMgr: LinearLayoutManager
    private var mAllowToLoadMore = true // chặn load khi không có dữ liệu
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
        val mAdapter =
            ListBookSchedulePatientAdapter(this@ListBookSchedulePatientActivity)
        listLayoutMgr =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val apiClient = ApiClient(this@ListBookSchedulePatientActivity)
        binding.apply {
            pbLoading.visible()
            lifecycleScope.launch(Dispatchers.IO) {
                val response =
                    apiClient.patientService.getListBookScheduleByPatient(1)
                withContext(Dispatchers.Main) {
                    pbLoading.gone()

                    if (response.isSuccessful()) {
                        response.data?.data?.let {
                            mBookSchedule = it
                            if (mBookSchedule.isEmpty()) {
                                tvNone.visible()
                                rvMedicalHistory.gone()
                            } else {
                                tvNone.gone()
                                rvMedicalHistory.visible()
                                rvMedicalHistory.layoutManager = listLayoutMgr
                                rvMedicalHistory.adapter = mAdapter
                                mAdapter.submitList(mBookSchedule)
                            }
                        }
                    } else {
                        toast(response.error?.error?.msg.toString())
                    }
                }
            }
            ivHome.setOnClickListener {
                val intent = Intent(
                    this@ListBookSchedulePatientActivity,
                    HomePatientActivity::class.java
                )
                startActivity(intent)
            }
            rvMedicalHistory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (listLayoutMgr.findLastVisibleItemPosition() == mBookSchedule.size - 1 && mAllowToLoadMore) {
                        mAllowToLoadMore = false
                        currentPage += 1
                        lifecycleScope.launch(Dispatchers.IO) {
                            val response =
                                apiClient.patientService.getListBookScheduleByPatient(currentPage)
                            withContext(Dispatchers.Main) {
                                pbLoading.gone()
                                if (response.isSuccessful()) {
                                    response.data?.data?.let {
                                        mBookSchedule = mBookSchedule + it
                                        mAdapter.submitList(mBookSchedule)
                                        mAllowToLoadMore = true
                                    }
                                } else {
                                    mAllowToLoadMore = false
                                    toast(response.error?.error?.msg.toString())
                                }
                            }
                        }
                    }
                }
            })
        }
    }
    override fun onStop() {
        super.onStop()
        binding.rvMedicalHistory.gone()
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