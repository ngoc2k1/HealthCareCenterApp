package com.example.myapplication.patient

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.FragmentNotificationBinding
import com.example.myapplication.doctor.DoctorListNotificationItemmAdapter
import com.example.myapplication.model.PatientNotificationResponse
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private var mListNotification: List<PatientNotificationResponse.Data> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }

        val apiClient = ApiClient(requireActivity())

        binding.apply {

//            lifecycleScope.launch(Dispatchers.IO) {
//                val notificationResponse = apiClient.patientService.getNotification()
//                if (notificationResponse.code == 200) {
//                    withContext(Dispatchers.Main)
//                    {
//                        mListNotification = notificationResponse.data
//                        toast(mListNotification.size.toString())
//                        if (mListNotification.isEmpty()) {
//                            tvNoneNotification.visible()
//                            rvNotification.gone()
//                        } else {
//                            tvNoneNotification.gone()
//                            rvNotification.visible()
//                            val notificationItemAdapter = PatientListNotificationItemmAdapter()
////        binding.pbMainLoadingvideo.visibility = View.VISIBLE
//                            binding.rvNotification.adapter = notificationItemAdapter
//                            notificationItemAdapter.submitList(mListNotification)
//                        }
//                    }
//                }
//            }
        }
    }
}