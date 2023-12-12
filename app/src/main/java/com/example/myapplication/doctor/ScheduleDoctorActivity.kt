package com.example.myapplication.doctor

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityDoctorWorkScheduleBinding
import com.example.myapplication.model.WorkScheduleResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant.ID_BOOKSCHEDULE
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleDoctorActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityDoctorWorkScheduleBinding
    private var mListSchedule: List<WorkScheduleResponse.WorkSchedule> = emptyList()
    private var currentPage = 1
    private lateinit var listLayoutMgr: LinearLayoutManager
    private var mAllowToLoadMore = true // chặn load khi không có dữ liệu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorWorkScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@ScheduleDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onResume() {
        super.onResume()
        val apiClient = ApiClient(this@ScheduleDoctorActivity)
        listLayoutMgr =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val scheduleItemAdapter =
            ScheduleDoctorItemmAdapter(this@ScheduleDoctorActivity)

        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val listSchedule = apiClient.doctorService.getListBookScheduleByDoctor(1)
                withContext(Dispatchers.Main) {
                    if (listSchedule.isSuccessful()) {
                        listSchedule.data?.data?.let {
                            mListSchedule = it
                            if (mListSchedule.isEmpty()) {
                                tvNoneSchedule.visible()
                                rcvWorkSchedule.gone()
                            } else {
                                tvNoneSchedule.gone()
                                rcvWorkSchedule.visible()
                                rcvWorkSchedule.layoutManager = listLayoutMgr
                                rcvWorkSchedule.adapter = scheduleItemAdapter
                                scheduleItemAdapter.submitList(mListSchedule)
                            }
                        }
                    } else {
                        toast(listSchedule.error?.error?.msg.toString())
                    }
                }
            }
            rcvWorkSchedule.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (listLayoutMgr.findLastVisibleItemPosition() == mListSchedule.size - 1 && mAllowToLoadMore) {
                        mAllowToLoadMore = false
                        currentPage += 1
                        lifecycleScope.launch(Dispatchers.IO) {
                            val listSchedule =
                                apiClient.doctorService.getListBookScheduleByDoctor(currentPage)
                            withContext(Dispatchers.Main) {
                                if (listSchedule.isSuccessful()) {
                                    listSchedule.data?.data?.let {
                                        mListSchedule = mListSchedule + it
                                        scheduleItemAdapter.submitList(mListSchedule)
                                        mAllowToLoadMore = true
                                    }
                                } else {
                                    mAllowToLoadMore = false
                                    toast(listSchedule.error?.error?.msg.toString())
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    override fun getDetailSchedule(id: Int) {
        val intent = Intent(this@ScheduleDoctorActivity, DetailScheduleActivity::class.java)
        intent.putExtra(ID_BOOKSCHEDULE, id)
        startActivity(intent)
    }
}