package com.example.myapplication.doctor

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBookScheduleDetailBinding
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant.ID_BOOKSCHEDULE
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.convertStatusBook
import com.example.myapplication.utils.generateQR
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailScheduleActivity : AppCompatActivity() {
    private lateinit var binding: FragmentBookScheduleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBookScheduleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@DetailScheduleActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val apiClient = ApiClient(this@DetailScheduleActivity)
        val idBookSchedule = intent.getIntExtra(ID_BOOKSCHEDULE, 0)
        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val schedule = apiClient.doctorService.getDetailBookSchedule(idBookSchedule)
                if (schedule.code == 200) {
                    withContext(Dispatchers.Main) {
                        schedule.data.doctor.apply {
                            tvAddressAppointmentTitle.text = addressTest
                            tvSpecialty.text = specialty.name
                        }
                        schedule.data.patient.apply {
                            Glide.with(ivAvatarPatient).load(avatar).centerCrop()
                                .placeholder(R.drawable.img_default_avatar_home)
                                .into(ivAvatarPatient)
                            tvNamePatient.text = name
                            tvGenderAgePatient.text = convertGender(gender) + ", " + age + " tuổi"
                        }
                        schedule.data.apply {
                            ivQr.setImageBitmap(qrCode.generateQR())
                            tvStatus.text = convertStatusBook(statusBook, tvStatus)
                            tvTimeTest.text = timeTest
                            tvDateTest.text = dateTest
                            if (statusHealth == "") {
                                tvStatusHealth.gone()
                                tvStatusHealthTitle.gone()
                            } else {
                                tvStatusHealth.text = statusHealth
                            }
                            tvPrice.text = price.toString()
                        }
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                    }
                }
            }
            btnConfirm.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val confirmSchedule =
                        apiClient.doctorService.confirmPatientTested(idBookSchedule)
                    if (confirmSchedule.code == 200) {
                        withContext(Dispatchers.Main) {
                            toast(confirmSchedule.msg)
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                        }
                    }
                }
            }
            btnEdit.setOnClickListener {
//                val intent = Intent(this@DetailScheduleActivity, DetailScheduleActivity::class.java)
//                intent.putExtra(ID_MEDICALHISTORY, idBookSchedule)
//                startActivity(intent)
            }
        }
    }
}