package com.example.myapplication.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDialogForgotPwBinding
import com.example.myapplication.model.ChangePwRequest
import com.example.myapplication.model.ResetPwActiveRequest
import com.example.myapplication.model.ResetPwRequest
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.toast
import com.example.myapplication.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordDialog : DialogFragment() {
    private lateinit var binding: FragmentDialogForgotPwBinding
    var mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogForgotPwBinding.inflate(layoutInflater)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            if (mContext != null) {
                val apiClient = ApiClient(mContext!!)
                buttonOk.setOnClickListener {
                    val email = edtEmail.text.toString()

                    if (email.isBlank()) {
                        mContext!!.toast(mContext!!.getString(R.string.str_error_change_pw))
                    } else {
                        lifecycleScope.launch(Dispatchers.IO) {
                            val changePwResponse =
                                apiClient.doctorService.sendEmail(ResetPwActiveRequest(email))
                            if (changePwResponse.code == 200) {
                                withContext(Dispatchers.Main) {
                                    groupActive.visible()
                                    groupForgot.gone()
                                    buttonOkActive.setOnClickListener {
                                        val otp = edtOtp.text.toString()
                                        val pw = edtPw.text.toString()

                                        if (otp.isBlank() || pw.isBlank()) {
                                            mContext!!.toast(mContext!!.getString(R.string.str_error_change_pw))
                                        } else {
                                            lifecycleScope.launch(Dispatchers.IO) {
                                                val activePasswordResponse =
                                                    apiClient.doctorService.sendNewPassword(
                                                        ResetPwRequest(pw, otp)
                                                    )
                                                if (activePasswordResponse.code == 200) {
                                                    withContext(Dispatchers.Main) {
                                                        mContext!!.toast(activePasswordResponse.msg)
                                                        dismiss()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            buttonCancel.setOnClickListener {
                dismiss()
            }
            buttonCancelActive.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onStart() {//sd khi layout là constraint
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
