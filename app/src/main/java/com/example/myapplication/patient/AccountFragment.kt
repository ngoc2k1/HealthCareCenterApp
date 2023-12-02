package com.example.myapplication.patient

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.auth.ChangePwDialog
import com.example.myapplication.auth.LoginActivity
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.prefs.HawkKey
import com.example.myapplication.utils.Constant
import com.example.myapplication.utils.getCurrentHour
import com.example.myapplication.utils.toast
import com.orhanobut.hawk.Hawk

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val bundle: Bundle? = intent.extras
        binding.apply {
//            if (bundle != null) {
//                val avatar = bundle.getString(Constant.AVT_DOCTOR)
//                val name = bundle.getString(Constant.NAME_DOCTOR)
//
//                Glide.with(imgAvatar).load(avatar).centerCrop()
//                    .placeholder(R.drawable.img_default_avatar_home)
//                    .into(imgAvatar)
//                tvName.text = name
//            }
            optionChangePassword.setOnClickListener {
//                val changePwDialog = ChangePwDialog(this@AccountFragment)
//                changePwDialog.show(childFragmentManager, "dialog")//supportFM : activity
            }

            optionChangeAccount.setOnClickListener {
                Hawk.put(HawkKey.ACCESS_TOKEN_PATIENT, "")
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.viewContain.apply {
            if (getCurrentHour() in 6..18) {
                setBackgroundResource(R.drawable.background_app_sun)
            } else {
                setBackgroundResource(R.drawable.background_app)
            }
        }
    }
}