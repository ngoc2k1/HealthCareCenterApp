package com.example.myapplication.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.ActivityDoctorWorkScheduleBinding

class ScheduleFragment : Fragment() {
    private var _binding: ActivityDoctorWorkScheduleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityDoctorWorkScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }
}