package com.example.myapplication.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.FragmentDialogChangePwBinding

interface OnClickListenerChangePwDialog {
    fun addKeyResult()
}

class ChangePwDialog: DialogFragment(
) {
    private lateinit var binding: FragmentDialogChangePwBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogChangePwBinding.inflate(inflater, container, false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
    }

    override fun onStart() {//sd khi layout là constraint
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    private fun setupClickListener() {
        with(binding) {

            buttonOk.setOnClickListener {
                val currentPassword = edtCurrentPw.text.toString()
                val confirmPassword = edtCfPw.text.toString()
                val newPassword = edtNewPw.text.toString()
                var isCheck = true

                if (currentPassword.isBlank() || confirmPassword.isBlank() || newPassword.isBlank()) {
                    isCheck = false
                }

                if (isCheck) {
                    //call api
                    dismiss()
                }
            }

            buttonCancel.setOnClickListener {
                dismiss()
            }
        }
    }
}
