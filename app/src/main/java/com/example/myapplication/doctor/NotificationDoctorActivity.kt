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
import com.example.myapplication.databinding.ActivityDoctorNotificationBinding
import com.example.myapplication.model.DoctorNotificationResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorNotificationBinding
    private var mListNotification: List<DoctorNotificationResponse.Data> = emptyList()
    private var currentPage = 1
    private lateinit var listLayoutMgr: LinearLayoutManager
    private var mAllowToLoadMore = true // chặn load khi không có dữ liệu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@NotificationDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        val apiClient = ApiClient(this@NotificationDoctorActivity)
        listLayoutMgr =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val notificationItemAdapter = DoctorListNotificationItemmAdapter()

        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val notificationResponse = apiClient.doctorService.getNotification(1)
                withContext(Dispatchers.Main)
                {
                    if (notificationResponse.isSuccessful()) {
                        notificationResponse.data?.data?.let {
                            mListNotification = it
                            if (mListNotification.isEmpty()) {
                                tvNoneNotification.visible()
                                rvNotification.gone()
                            } else {
                                tvNoneNotification.gone()
                                rvNotification.visible()
                                rvNotification.layoutManager = listLayoutMgr
                                rvNotification.adapter = notificationItemAdapter
                                notificationItemAdapter.submitList(mListNotification)
                            }
                        }
                    }else {
                        toast(notificationResponse.error?.error?.msg.toString())
                    }
                }
            }
            rvNotification.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (listLayoutMgr.findLastVisibleItemPosition() == mListNotification.size - 1 && mAllowToLoadMore) {
                        mAllowToLoadMore = false
                        currentPage += 1
                        lifecycleScope.launch(Dispatchers.IO) {
                            val notificationResponse = apiClient.doctorService.getNotification(currentPage)
                            withContext(Dispatchers.Main) {
                                if (notificationResponse.isSuccessful()) {
                                    notificationResponse.data?.data?.let {
                                        mListNotification = mListNotification + it
                                        notificationItemAdapter.submitList(mListNotification)
                                        mAllowToLoadMore = true
                                    }
                                } else {
                                    mAllowToLoadMore = false
                                    toast(notificationResponse.error?.error?.msg.toString())
                                }
                            }
                        }
                    }
                }
            })

        }
    }
}