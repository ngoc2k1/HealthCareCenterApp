package com.example.myapplication.doctor

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMedicalHistoryDetailBinding
import com.example.myapplication.model.MedicalHistoryUpdateRequest
import com.example.myapplication.model.PhotoUI
import com.example.myapplication.patient.HomePatientActivity
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant.FIRST_MEDICALHISTORY
import com.example.myapplication.utils.Constant.ID_MEDICALHISTORY
import com.example.myapplication.utils.Constant.IS_PATIENT
import com.example.myapplication.utils.Constant.PICK_IMAGE_MULTIPLE
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Arrays


class DetailMedicalHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicalHistoryDetailBinding
    private var photoList = ArrayList<String>()
    var photoListObject = ArrayList<PhotoUI>()
    private lateinit var photoListItemAdapter: PhotoListItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@DetailMedicalHistoryActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        photoListItemAdapter = PhotoListItemAdapter()
        val apiClient = ApiClient(this@DetailMedicalHistoryActivity)
        val idMedicalHistory = intent.getIntExtra(ID_MEDICALHISTORY, 0)
        val isPatient = intent.getIntExtra(IS_PATIENT, 0)
        val firstMedicalHistory = intent.getIntExtra(FIRST_MEDICALHISTORY, 0)
        binding.apply {
            tvTestResult.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                startActivityForResult(intent, PICK_IMAGE_MULTIPLE)
            }
            if (isPatient == 1) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val medicalHistory =
                        apiClient.patientService.getDetailMedicalHistory(idMedicalHistory)
                    withContext(Dispatchers.Main) {
                        if (medicalHistory.isSuccessful()) {
                            medicalHistory.data?.data?.let {
                                it.apply {
                                    if (judgmentNote.isNullOrBlank()) edtJudgmentNote.setText("")
                                    else
                                        edtJudgmentNote.setText(judgmentNote)
                                    if (prescription.isNullOrBlank()) edtPrescription.setText("")
                                    else
                                        edtPrescription.setText(prescription)

                                    if (retestDate.isNullOrBlank()) edtRetestDate.setText("")
                                    else
                                        edtRetestDate.setText(retestDate)
                                    testResult?.let {
                                        val arrayString = ArrayList<String>(
                                            Arrays.asList<String>(testResult)
                                        )
                                        val b = arrayString[0]
                                        val cleanedString =
                                            b.replace("[", "").replace("]", "").trim()
                                        val elements = cleanedString.split(",").map { it.trim() }
                                        val arrayList = ArrayList(elements)
                                        for (i in arrayList) {
                                            photoList.add(i)
                                            photoListObject.add(PhotoUI(i))
                                        }
                                        val gridLayoutManager = GridLayoutManager(
                                            applicationContext, 2
                                        )
                                        gridLayoutManager.orientation =
                                            LinearLayoutManager.VERTICAL
                                        if (!photoListObject.isEmpty()) {
                                            binding.rvTestReult.visible()
                                            binding.rvTestReult.layoutManager = gridLayoutManager
                                            binding.rvTestReult.adapter = photoListItemAdapter
                                            photoListItemAdapter.submitList(photoListObject)
                                        }
                                    }

                                    bookSchedule.apply {
                                        tvDateTest.text = "Thời gian khám: $dateTest"
                                        if (namePatientTest.isNullOrBlank()) tvPatient.gone()
                                        else tvPatient.text = "Tên bệnh nhân: $namePatientTest"
                                        doctor.apply {
                                            tvAddress.text = "Địa chỉ khám: $addressTest"
                                            tvNameDoctor.text = name
                                            Glide.with(imgAvatar).load(avatar).centerCrop()
                                                .placeholder(R.drawable.img_default_avatar_home)
                                                .into(imgAvatar)
                                            specialty.apply {
                                                Glide.with(ivSpecialty).load(image).centerCrop()
                                                    .placeholder(R.drawable.ic_hepatitis_transmission_black)
                                                    .into(ivSpecialty)
                                                tvSpecialty.text = name
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            toast(medicalHistory.error?.error?.msg.toString())
                        }
                    }
                }

                tvSave.gone()
                tvEdit.gone()
                ivHome.setOnClickListener {
                    val intent = Intent(
                        this@DetailMedicalHistoryActivity,
                        HomePatientActivity::class.java
                    )
                    startActivity(intent)
                }
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    val medicalHistory =
                        apiClient.doctorService.getDetailMedicalHistory(idMedicalHistory)
                    withContext(Dispatchers.Main) {
                        if (medicalHistory.isSuccessful()) {
                            medicalHistory.data?.data?.let {
                                it.apply {
                                    if (judgmentNote.isNullOrBlank()) edtJudgmentNote.setText("")
                                    else
                                        edtJudgmentNote.setText(judgmentNote)
                                    if (prescription.isNullOrBlank()) edtPrescription.setText("")
                                    else
                                        edtPrescription.setText(prescription)

                                    if (retestDate.isNullOrBlank()) edtRetestDate.setText("")
                                    else
                                        edtRetestDate.setText(retestDate)
                                    testResult?.let {
                                        val arrayString = ArrayList<String>(
                                            Arrays.asList<String>(testResult)
                                        )
                                        val b = arrayString[0]
                                        val cleanedString =
                                            b.replace("[", "").replace("]", "").trim()
                                        val elements =
                                            cleanedString.split(",").map { it.trim() }
                                        val arrayList = ArrayList(elements)
                                        for (i in arrayList) {
                                            photoList.add(i)
                                            photoListObject.add(PhotoUI(i))
                                        }
                                        val gridLayoutManager = GridLayoutManager(
                                            applicationContext, 2
                                        )
                                        gridLayoutManager.orientation =
                                            LinearLayoutManager.VERTICAL
                                        if (testResult != "" && testResult != "[]") {
                                            rvTestReult.visible()
                                            rvTestReult.layoutManager =
                                                gridLayoutManager
                                            rvTestReult.adapter = photoListItemAdapter
                                            photoListItemAdapter.submitList(photoListObject)
                                        } else {
                                            tvDelete.gone()
                                            rvTestReult.gone()
                                        }
                                    }

                                    bookSchedule.apply {
                                        tvDateTest.text = "Thời gian khám: $dateTest"
                                        if (namePatientTest.isNullOrBlank()) tvPatient.gone()
                                        else tvPatient.text = "Tên bệnh nhân: $namePatientTest"
                                        doctor.apply {
                                            tvAddress.text = "Địa chỉ khám: $addressTest"
                                            tvNameDoctor.text = name
                                            Glide.with(imgAvatar).load(avatar).centerCrop()
                                                .placeholder(R.drawable.img_default_avatar_home)
                                                .into(imgAvatar)
                                            specialty.apply {
                                                Glide.with(ivSpecialty).load(image).centerCrop()
                                                    .placeholder(R.drawable.ic_hepatitis_transmission_black)
                                                    .into(ivSpecialty)
                                                tvSpecialty.text = name
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            toast(medicalHistory.error?.error?.msg.toString())
                        }
                    }
                }

                if (firstMedicalHistory == 2) {
                    tvDelete.visible()
                    edtJudgmentNote.isEnabled = true
                    tvTestResult.isEnabled = true
                    edtPrescription.isEnabled = true
                    edtRetestDate.isEnabled = true
                    edtRetestDate.hint = "dd/mm/yyyy"
                    val retestDate = edtRetestDate.text.toString()
                    if (retestDate.isNotBlank())
                        if (retestDate.length > 10) edtRetestDate.setText(retestDate.substring(9))
                    tvSave.visible()
                    tvEdit.gone()
                }
                tvEdit.setOnClickListener {
                    tvDelete.visible()
                    edtJudgmentNote.isEnabled = true
                    tvTestResult.isEnabled = true
                    edtPrescription.isEnabled = true
                    edtRetestDate.isEnabled = true
                    edtRetestDate.hint = "dd/mm/yyyy"
                    val retestDate = edtRetestDate.text.toString()
                    if (retestDate.isNotBlank())
                        if (retestDate.length > 10) edtRetestDate.setText(retestDate.substring(9))
                    tvSave.visible()
                    tvEdit.gone()
                }
                ivHome.setOnClickListener {
                    val intent = Intent(
                        this@DetailMedicalHistoryActivity,
                        HomeDoctorActivity::class.java
                    )
                    startActivity(intent)
                }
                tvSave.setOnClickListener {
                    edtRetestDate.hint = ""
                    lifecycleScope.launch(Dispatchers.IO) {
                        val updateMedicalHistory =
                            apiClient.doctorService.updateMedicalHistory(
                                idMedicalHistory,
                                MedicalHistoryUpdateRequest(
                                    edtJudgmentNote.text.toString(),
                                    edtPrescription.text.toString(),
                                    edtRetestDate.text.toString(),
                                    photoList.toString()
                                )
                            )
                        withContext(Dispatchers.Main) {
                            if (updateMedicalHistory.isSuccessful()) {
                                toast(updateMedicalHistory.data?.msg.toString())
                            } else {
                                toast(updateMedicalHistory.error?.error?.msg.toString())
                            }
                        }
                    }
                    tvSave.gone()
                    tvDelete.gone()
                    tvEdit.visible()
                    edtJudgmentNote.isEnabled = false
                    edtPrescription.isEnabled = false
                    edtRetestDate.isEnabled = false
                    tvTestResult.isEnabled = false
                }
            }
            tvDelete.setOnClickListener {
                photoListObject.clear()
                photoList.clear()
                rvTestReult.gone()
                tvDelete.gone()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK) {
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    photoList.add(imageUri.toString())
                }
            } else if (data?.data != null) {
                val imageUri = data.data
                photoList.add(imageUri.toString())
            }
            photoListObject.clear()
            for (i in photoList) {
                photoListObject.add(PhotoUI(i))
            }
            binding.tvDelete.visible()
            binding.rvTestReult.visible()
            val gridLayoutManager = GridLayoutManager(
                applicationContext, 2
            )
            gridLayoutManager.orientation =
                LinearLayoutManager.VERTICAL
            binding.rvTestReult.visible()
            binding.rvTestReult.layoutManager = gridLayoutManager
            binding.rvTestReult.adapter = photoListItemAdapter//
//            photoListItemAdapter.notifyDataSetChanged()
            photoListItemAdapter.submitList(photoListObject)
        }
    }
}