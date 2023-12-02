package com.example.myapplication.doctor

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityDoctorWorkScheduleBinding
import com.example.myapplication.model.WorkScheduleResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant.AVT_DOCTOR
import com.example.myapplication.utils.Constant.ID_BOOKSCHEDULE
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleDoctorActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityDoctorWorkScheduleBinding
    private var mListSchedule: List<WorkScheduleResponse.WorkSchedule> = emptyList()

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
        val apiClient = ApiClient(this@ScheduleDoctorActivity)

        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val listSchedule = apiClient.doctorService.getListBookScheduleByDoctor()
                if (listSchedule.code == 200) {
                    mListSchedule = listSchedule.data
                    if (mListSchedule.isEmpty()) {
                        tvNoneSchedule.visible()
                        rcvWorkSchedule.gone()
                    } else {
                        tvNoneSchedule.gone()
                        rcvWorkSchedule.visible()
                        withContext(Dispatchers.Main) {
                            val scheduleItemAdapter =
                                ScheduleDoctorItemmAdapter(this@ScheduleDoctorActivity)
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                            binding.rcvWorkSchedule.adapter = scheduleItemAdapter

                            scheduleItemAdapter.submitList(mListSchedule)
                        }
                    }
                }
            }
        }
    }

    override fun getDetailSchedule(id: Int) {
        val intent = Intent(this@ScheduleDoctorActivity, DetailScheduleActivity::class.java)
        intent.putExtra(ID_BOOKSCHEDULE, id)
        startActivity(intent)
    }
}