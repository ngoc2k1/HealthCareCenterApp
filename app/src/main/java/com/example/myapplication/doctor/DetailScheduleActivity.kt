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
import com.example.myapplication.patient.HomePatientActivity
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant.ID_BOOKSCHEDULE
import com.example.myapplication.utils.Constant.ID_MEDICALHISTORY
import com.example.myapplication.utils.STATUS_BOOK
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.convertStatusBook
import com.example.myapplication.utils.generateQR
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
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
            ivEdit.gone()
            ivHome.setOnClickListener {
                val intent = Intent(
                    this@DetailScheduleActivity,
                    HomeDoctorActivity::class.java
                )
                startActivity(intent)
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val schedule = apiClient.doctorService.getDetailBookSchedule(idBookSchedule)
                withContext(Dispatchers.Main) {
                    if (schedule.isSuccessful()) {
                        schedule.data?.data?.doctor?.let {
                            it.apply {
                                tvAddress.text = addressTest
                                tvSpecialtyDoctor.text = specialty.name
                            }
                        }
                        schedule.data?.data?.patient?.let {
                            it.apply {
                                Glide.with(imgAvatar).load(avatar).centerCrop()
                                    .placeholder(R.drawable.img_default_avatar_home)
                                    .into(imgAvatar)
                                tvNamePatient.text = name
                                tvGenderAgePatient.text =
                                    convertGender(gender) + ", " + age + " tuổi"
                            }
                        }
                        schedule.data?.data?.let {
                            it.apply {
                                ivQr.setImageBitmap(qrCode.generateQR())
                                when (statusBook) {
                                    STATUS_BOOK.DA_KHAM.toString() -> {
                                        tvStatus.setTextColor(resources.getColor(R.color.green))
                                        clChuaKham.gone()
                                        clDaKham.visible()
                                    }

                                    STATUS_BOOK.DA_HUY.toString() -> {
                                        tvStatus.setTextColor(resources.getColor(R.color.red))
                                        clChuaKham.gone()
                                        clDaKham.gone()
                                    }

                                    else -> {
                                        ivQr.visible()
                                        clChuaKham.visible()
                                        clDaKham.gone()
                                        tvStatus.setTextColor(resources.getColor(R.color.blue))
                                    }
                                }
                                tvStatus.text = convertStatusBook(statusBook, tvStatus)
                                val mTime = timeTest
                                val first = mTime.split("-")[0].split(":")[0].trim().toInt()
                                val second = mTime.split("-")[1].split(":")[0].trim().toInt()
                                if (first < second) tvTimeTest.text = "${mTime.split("-")[0]}-${mTime.split("-")[1]}"
                                else tvTimeTest.text = "${mTime.split("-")[1]}-${mTime.split("-")[0]}"
                                tvDateTest.text = dateTest
                                if (statusHealth == "") {
                                    clHealth.gone()
                                } else {
                                    clHealth.visible()
                                    tvStatusHealth.text = statusHealth
                                }
                                tvPrice.text = "$price đồng"
                            }
                        }
                    } else {
                        toast(schedule.error?.error?.msg.toString())
                    }
                }
            }

            btnConfirmCk.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val confirmSchedule =
                        apiClient.doctorService.confirmPatientTested(idBookSchedule)
                    withContext(Dispatchers.Main) {
                        if (confirmSchedule.isSuccessful()) {
                            tvStatus.setTextColor(resources.getColor(R.color.green))
                            tvStatus.text =
                                convertStatusBook(STATUS_BOOK.DA_KHAM.toString(), tvStatus)
                            clChuaKham.gone()
                            ivQr.gone()
                            clDaKham.visible()
                            toast(confirmSchedule.data?.msg.toString())
                        }
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
                        else {
                            toast(confirmSchedule.error?.error?.msg.toString())
                        }
                    }
                }
            }


            btnResultDk.setOnClickListener {
                val intent =
                    Intent(this@DetailScheduleActivity, DetailMedicalHistoryActivity::class.java)
                intent.putExtra(ID_MEDICALHISTORY, idBookSchedule)
                startActivity(intent)
            }
        }
    }
}