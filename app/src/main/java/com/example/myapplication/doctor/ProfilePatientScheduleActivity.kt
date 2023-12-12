package com.example.myapplication.doctor

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfilePatientBinding
import com.example.myapplication.model.MedicalHistoryListDoctorResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.Constant.AVT_FEMALE
import com.example.myapplication.utils.Constant.AVT_MALE
import com.example.myapplication.utils.Constant.ID_PATIENT
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfilePatientScheduleActivity : AppCompatActivity(), OnMedicalHistoryClick {
    private lateinit var binding: FragmentProfilePatientBinding
    var context: Context? = null
    private var mMedicalHistory: List<MedicalHistoryListDoctorResponse.Data> = emptyList()
    private var currentPage = 1
    private lateinit var listLayoutMgr: LinearLayoutManager
    private var mAllowToLoadMore = true // chặn load khi không có dữ liệu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfilePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@ProfilePatientScheduleActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val apiClient = ApiClient(this@ProfilePatientScheduleActivity)
        val idPatient = intent.getIntExtra(ID_PATIENT, 0)
        listLayoutMgr =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val medicalHistoryItemAdapter =
            MedicalHistoryListPatientItemmAdapter(this@ProfilePatientScheduleActivity)

        binding.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                val medicalHistoryResponse =
                    apiClient.doctorService.getListMedicalHistoryByDoctor(idPatient,1)
                withContext(Dispatchers.Main) {
                    if (medicalHistoryResponse.isSuccessful()) {
                        medicalHistoryResponse.data?.data?.let {
                            mMedicalHistory = it
                            if (mMedicalHistory.isEmpty()) {
                                tvNone.visible()
                                rvMedicalHistory.gone()
                            } else {
                                tvNone.gone()
                                rvMedicalHistory.visible()
                                rvMedicalHistory.layoutManager = listLayoutMgr
                                rvMedicalHistory.adapter = medicalHistoryItemAdapter
                                medicalHistoryItemAdapter.submitList(mMedicalHistory)
                            }
                        }
                    } else {
                        toast(medicalHistoryResponse.error?.error?.msg.toString())
                    }
                }
            }
            rvMedicalHistory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (listLayoutMgr.findLastVisibleItemPosition() == mMedicalHistory.size - 1 && mAllowToLoadMore) {
                        mAllowToLoadMore = false
                        currentPage += 1
                        lifecycleScope.launch(Dispatchers.IO) {
                            val medicalHistoryResponse =
                                apiClient.doctorService.getListMedicalHistoryByDoctor(idPatient,currentPage)
                            withContext(Dispatchers.Main) {
                                if (medicalHistoryResponse.isSuccessful()) {
                                    medicalHistoryResponse.data?.data?.let {
                                        mMedicalHistory = mMedicalHistory + it
                                        medicalHistoryItemAdapter.submitList(mMedicalHistory)
                                        mAllowToLoadMore = true
                                    }
                                } else {
                                    mAllowToLoadMore = false
                                    toast(medicalHistoryResponse.error?.error?.msg.toString())
                                }
                            }
                        }
                    }
                }
            })

            lifecycleScope.launch(Dispatchers.IO) {
                val patient =
                    apiClient.doctorService.getPatientByDoctor(idPatient)
                withContext(Dispatchers.Main) {
                    if (patient.isSuccessful()) {
                        patient.data?.data?.let {
                            it.apply {
                                avatar = if (gender == GENDER.FEMALE.toString())
                                    AVT_FEMALE
                                else AVT_MALE
                                Glide.with(imgAvatar).load(avatar)
                                    .centerCrop()
                                    .placeholder(R.drawable.img_default_avatar_home)
                                    .into(imgAvatar)
                                tvName.text = name
                                tvBlood.text = bloodGroup
                                tvWeight.text = "$weight kg"
                                tvHeight.text = "$height cm"
                                tvGenderAge.text = convertGender(gender) + ", " + age + " tuổi"
                            }
                        }
                    } else {
                        toast(patient.error?.error?.msg.toString())
                    }
                }
            }
        }
    }

    override fun getDetailMedicalHistory(id: Int) {
        val intent =
            Intent(this@ProfilePatientScheduleActivity, DetailMedicalHistoryActivity::class.java)
        intent.putExtra(Constant.ID_MEDICALHISTORY, id)
        startActivity(intent)
    }
}