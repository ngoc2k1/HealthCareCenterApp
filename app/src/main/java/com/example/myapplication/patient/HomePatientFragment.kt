package com.example.myapplication.patient

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomePatientBinding
import com.example.myapplication.doctor.AccountDoctorActivity
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.getCurrentHour
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePatientFragment : Fragment() {
    private var _binding: FragmentHomePatientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        val apiClient = ApiClient(requireContext())
        binding.apply {

//            GlobalScope.launch(Dispatchers.IO) {
//                val patient = apiClient.patientService.getPatient()
//                if (patient.code == 200) {
//                    withContext(Dispatchers.Main)
//                    {
//                        Glide.with(imgAvatar).load(patient.data.avatar).centerCrop()
//                            .placeholder(R.drawable.img_default_avatar_home)
//                            .into(imgAvatar)
//                        tvFullName.text = patient.data.name
//                        tvBlood.text = patient.data.bloodGroup
//                        tvWeight.text = patient.data.weight.toString() + " kg"
//                        tvHeight.text = patient.data.height.toString() + " cm"
////                        bundle.putString(Constant.NAME_PATIENT, patient.data.name)
////                        bundle.putString(Constant.AVT_PATIENT, patient.data.avatar)
//                    }
//                }
//            }

            tvBookSchedule.setOnClickListener {
//                val intent = Intent(requireContext(), AccountDoctorActivity::class.java)
//                intent.putExtras(bundle)
//                startActivity(intent)
            }

            tvMedicalRecords.setOnClickListener {
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            if (getCurrentHour() in 6..18) {
                tvGreeting.text = getString(R.string.str_hello_user)
                viewContain.setBackgroundResource(R.drawable.background_app_sun)
            } else {
                tvGreeting.text = getString(R.string.str_hello_user_evening)
                viewContain.setBackgroundResource(R.drawable.background_app)
            }
        }
    }
}