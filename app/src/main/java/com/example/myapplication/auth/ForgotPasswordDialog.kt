package com.example.myapplication.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.FragmentDialogForgotPwBinding

class ForgotPasswordDialog : DialogFragment(
) {
    private lateinit var binding: FragmentDialogForgotPwBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogForgotPwBinding.inflate(layoutInflater)
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
                val phone = edtPhone.text.toString()
                if (phone.isNotBlank()) {
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
