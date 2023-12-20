package com.example.myapplication.patient

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
import com.example.myapplication.doctor.DetailMedicalHistoryActivity
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.Constant.ID_BOOKSCHEDULE
import com.example.myapplication.utils.Constant.ID_DOCTOR
import com.example.myapplication.utils.Constant.ID_MEDICALHISTORY
import com.example.myapplication.utils.STATUS_BOOK
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.convertStatusBook
import com.example.myapplication.utils.generateQR
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailSchedulePatientActivity : AppCompatActivity() {
    private lateinit var binding: FragmentBookScheduleDetailBinding
    private  var idDoctor=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBookScheduleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@DetailSchedulePatientActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val apiClient = ApiClient(this@DetailSchedulePatientActivity)
        val idBookSchedule = intent.getIntExtra(ID_BOOKSCHEDULE, 0)
        binding.apply {
            ivEdit.setOnClickListener {
                val intent = Intent(
                    this@DetailSchedulePatientActivity,
                    BookScheduleUpdatePatientActivity::class.java
                )
                intent.putExtra(ID_BOOKSCHEDULE, idBookSchedule)
                intent.putExtra(ID_DOCTOR, idDoctor)
                startActivity(intent)
            }
            llInformationPatient.gone()
            clTop.visible()
            clDaHuy.visible()
            clDaKham.gone()
            clChuaKham.gone()
            btnConfirmDh.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val schedule = apiClient.patientService.cancelBookSchedule(idBookSchedule)
                    withContext(Dispatchers.Main) {
                        if (schedule.isSuccessful()) {
                            toast(schedule.data?.msg.toString())
                            btnConfirmDh.gone()
                        } else {
                            toast(schedule.error?.error?.msg.toString())
                        }
                    }
                }
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val schedule = apiClient.patientService.getDetailBookSchedule(idBookSchedule)
                withContext(Dispatchers.Main) {
                    if (schedule.isSuccessful()) {
                        schedule.data?.data?.doctor?.let {
                            it.apply {
                                tvAddress.text = addressTest
                                tvNameDoctor.text = name
                                Glide.with(ivAvatarDoctor).load(avatar).centerCrop()
                                    .placeholder(R.drawable.img_default_avatar_home)
                                    .into(ivAvatarDoctor)
                                tvSpecialtyDoctor.text = specialty.name
                                idDoctor=id
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
                                        clDaHuy.gone()
                                        clDaKham.visible()
                                    }

                                    STATUS_BOOK.DA_HUY.toString() -> {
                                        tvStatus.setTextColor(resources.getColor(R.color.red))
                                        clChuaKham.gone()
                                        clDaHuy.gone()
                                        clDaKham.gone()
                                    }

                                    else -> {
                                        ivEdit.visible()
                                        ivQr.visible()
                                        clChuaKham.gone()
                                        clDaHuy.visible()
                                        clDaKham.gone()
                                        tvStatus.setTextColor(resources.getColor(R.color.blue))
                                    }
                                }
                                tvStatus.text = convertStatusBook(statusBook, tvStatus)
                                tvTimeTest.text = timeTest
                                tvDateTest.text = dateTest
                                if (namePatientTest.isNotBlank()) {
                                    clPatientTest.visible()
                                    tvNameTestData.text = namePatientTest
                                } else clPatientTest.gone()
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

            btnResultDk.setOnClickListener {
                val intent = Intent(
                    this@DetailSchedulePatientActivity,
                    DetailMedicalHistoryActivity::class.java
                )
                intent.putExtra(ID_MEDICALHISTORY, idBookSchedule)
                intent.putExtra(Constant.IS_PATIENT, 1)
                startActivity(intent)
            }
        }
    }
}