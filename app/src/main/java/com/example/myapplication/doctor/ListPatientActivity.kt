package com.example.myapplication.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import com.example.myapplication.model.patient.PatientModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorListPatientBinding
import java.util.Timer
import java.util.TimerTask

class ListPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorListPatientBinding
    private lateinit var patientItemAdapter: ListPatientItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorListPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.background_main)

        //set du lieu rv
        patientItemAdapter = ListPatientItemAdapter(this)
//        binding.pbMainLoadingvideo.visibility = View.VISIBLE
//patientItemAdapter;.setNewData(patientList)
        patientItemAdapter.onClickListener = object : ListPatientItemAdapter.OnItemClickListener {
            override fun onItemClick(itemData: PatientModel) {
//                val intent = Intent(this@ListPatientActivity, ScheduleDoctorActivity::class.java)
//                startActivity(intent)
            }

        }
        binding.edtSearchPatient.addTextChangedListener(object : TextWatcher {
            var timer: Timer? = Timer()
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer?.cancel()
                timer = Timer()
                timer?.schedule(
                    object : TimerTask() {
                        override fun run() {
                            runOnUiThread {
//                                myViewModel.sendData(s.toString())
                            }
                        }
                    },
                    1000
                )
            }
        })
    }
}