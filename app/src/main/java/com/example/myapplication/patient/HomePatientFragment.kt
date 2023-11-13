package com.example.myapplication.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentHomePatientBinding

class HomePatientFragment : Fragment() {
    private var _binding: FragmentHomePatientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePatientBinding.inflate(inflater, container, false)
        return binding.root
    }
}