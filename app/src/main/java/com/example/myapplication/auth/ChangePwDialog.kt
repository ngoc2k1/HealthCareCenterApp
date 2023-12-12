package com.example.myapplication.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDialogChangePwBinding
import com.example.myapplication.model.ChangePwRequest
import com.example.myapplication.serviceapi.ApiClient
import com.example.myapplication.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChangePwDialog : DialogFragment() {
    private lateinit var binding: FragmentDialogChangePwBinding
    var mContext: Context? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogChangePwBinding.inflate(inflater, container, false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            if (mContext != null) {
                val apiClient = ApiClient(mContext!!)
                buttonOk.setOnClickListener {
                    val currentPassword = edtCurrentPw.text.toString()
                    val confirmPassword = edtCfPw.text.toString()
                    val newPassword = edtNewPw.text.toString()

                    if (currentPassword.isBlank() || confirmPassword.isBlank() || newPassword.isBlank()) {
                        mContext!!.toast(mContext!!.getString(R.string.str_error_change_pw))
                    } else {
                        if (newPassword == currentPassword) {
                            mContext!!.toast(mContext!!.getString(R.string.confirm_password_note_compare))
                        } else {
                            if (confirmPassword == newPassword) {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    val changePwResponse =
                                        apiClient.doctorService.changePassword(
                                            ChangePwRequest(newPassword, currentPassword)
                                        )
                                    withContext(Dispatchers.Main) {
                                        if (changePwResponse.isSuccessful()) {
                                            changePwResponse.data?.msg?.let { it1 ->
                                                mContext!!.toast(
                                                    it1
                                                )
                                            }
                                            dismiss()
                                        } else {
                                            toast(changePwResponse.error?.error?.msg.toString())
                                        }
                                    }
                                }
                            } else {
                                mContext!!.toast(mContext!!.getString(R.string.str_error_change_pw_cf))
                            }
                        }
                    }

                }
            }
            buttonCancel.setOnClickListener {
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
