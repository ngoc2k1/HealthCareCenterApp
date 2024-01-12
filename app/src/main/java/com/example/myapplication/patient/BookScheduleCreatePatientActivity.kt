package com.example.myapplication.patient

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
import com.example.myapplication.databinding.BookScheduleCreateBinding
import com.example.myapplication.firebase.APIService
import com.example.myapplication.firebase.Client
import com.example.myapplication.firebase.Data
import com.example.myapplication.firebase.FCMResponse
import com.example.myapplication.firebase.FCMSender
import com.example.myapplication.model.BookCreatedRequest
import com.example.myapplication.model.DateByDoctorUI
import com.example.myapplication.model.TimeByDoctorUI
import com.example.myapplication.prefs.Pref
import com.example.myapplication.receiver.AlarmService
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.Constant.ID_DOCTOR
import com.example.myapplication.utils.Constant.NAME_DOCTOR
import com.example.myapplication.utils.Constant.NAME_PATIENT
import com.example.myapplication.utils.Constant.PHONE_DOCTOR
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class BookScheduleCreatePatientActivity : AppCompatActivity(), OnTimeListener, OnDateListener {
    private lateinit var binding: BookScheduleCreateBinding
    private var mDate = ArrayList<DateByDoctorUI>()
    private var mTime = ArrayList<TimeByDoctorUI>()
    private var mTimeBooked = ""
    private var mDateBooked = ""
    private lateinit var timeListItemAdapter: TimeListItemAdapter
    private lateinit var dateListAdapter: DateListItemAdapter
    private var idDoctor = 0
    private var phoneDoctor = ""
    private var nameDoctor = ""
    private var namePatient = ""

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
        phoneDoctor = intent.getStringExtra(PHONE_DOCTOR).toString()
        nameDoctor = intent.getStringExtra(NAME_DOCTOR).toString()
        namePatient = Pref.getString(this, Constant.NAME_PATIENT_FB, "")
        Log.d("NGOCC", namePatient)
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
                            bookScheduleFirebase(phoneDoctor)

                        } else {
                            toast(response.error?.error?.msg.toString())
                        }
                    }
                }
            }
        }
    }

    val cal = Calendar.getInstance()
    private fun bookScheduleFirebase(phoneDoctor: String) {

        val h = mTimeBooked.split("-")[0].split(":")[0].trim().toInt()
        val mm = mTimeBooked.split("-")[0].split(":")[1].trim().toInt()
        val d = mDateBooked.split("/")[2].toInt()
        val m = mDateBooked.split("/")[1].toInt()
        val y = mDateBooked.split("/")[0].toInt()
//        val h = 18
//        val mm = 35
//        val d = 12
//        val m = 1
//        val y = 2024
//        10:33- 12/1/2023
Log.d("__aa",mTimeBooked+"- "+mDateBooked)
        cal.set(Calendar.YEAR, y)
        cal.set(Calendar.MONTH, m - 1) //0-11
        cal.set(Calendar.DAY_OF_MONTH, d)
        cal.set(Calendar.HOUR_OF_DAY, h)
        cal.set(Calendar.MINUTE, mm)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        cal.add(Calendar.HOUR, -8)
//        cal.add(Calendar.MINUTE, -2)
        getTokenFromFirebase(phoneDoctor)

        val mDateFomart = mDateBooked.split("/")[2] + "/" + mDateBooked.split("/")[1] + '/' + mDateBooked.split("/")[0]
        val idNotification = phoneDoctor + mDateFomart + mTimeBooked
        Log.d("__hihi", "bookScheduleFirebase:$idNotification ${idNotification.hashCode()} ")

        //098765432117/01/202409:00 - 10:00

        AlarmService(this).setExactAlarm(
            idNotification.hashCode(),
            cal.timeInMillis,
            "Thông báo nhắc nhở",
            "Bạn sắp có 1 lịch khám với bác sĩ $nameDoctor vào ngày $mDateBooked từ $mTimeBooked"
        )
    }

    private fun getTokenFromFirebase(sdt: String) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("tokens").document(sdt)
        docRef.get().addOnCompleteListener {
            if (isDestroyed || isFinishing) {
                return@addOnCompleteListener
            }
            if (it.isSuccessful) {
                if (it.result.data != null) {
                    val tokenBS = it.result.data!!["token"].toString()
                    pushNotification(tokenBS)
                }
                startActivity(
                    Intent(
                        this@BookScheduleCreatePatientActivity,
                        ListBookSchedulePatientActivity::class.java
                    )
                )
            } else {
                Log.d("__token", "error")
            }
        }

    }

    private fun pushNotification(tokenBS: String) {
        val data = Data()

        data.title = "Thông báo"
        data.content = "Bệnh nhân $namePatient đặt lịch vào ngày $mDateBooked từ $mTimeBooked"

        val fcmSender = FCMSender(tokenBS, data)
        val apiService: APIService = Client.retrofit!!.create(APIService::class.java)
        apiService.sendNotification(fcmSender)?.enqueue(object : Callback<FCMResponse?> {
            override fun onResponse(
                call: Call<FCMResponse?>,
                response: Response<FCMResponse?>
            ) {
                Log.d("__bookScheduleFirebase", "onResponse: ")
            }

            override fun onFailure(call: Call<FCMResponse?>, t: Throwable) {
                Log.d("__bookScheduleFirebase", "Throwable: ")
            }
        })
    }

    override fun getTime(doctorWorkScheduleId: Int, time: TimeByDoctorUI) {
        mTime.forEach {
            it.isClicked = it.value == time.value
        }
        val mmTime = time.value
        var mTimeFormat = ""
        val first = mmTime.split("-")[0].split(":")[0].trim().toInt()
        val second = mmTime.split("-")[1].split(":")[0].trim().toInt()
        mTimeFormat = if (first < second) "${mmTime.split("-")[0]}-${mmTime.split("-")[1]}"
        else "${mmTime.split("-")[1]}-${mmTime.split("-")[0]}"
        mTimeBooked = mTimeFormat
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
        mDateBooked = date.date
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