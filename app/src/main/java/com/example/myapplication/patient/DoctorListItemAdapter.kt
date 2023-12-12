package com.example.myapplication.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBookScheduleBinding
import com.example.myapplication.model.Specialty
import com.example.myapplication.model.DoctorBySpecialtyResponse

class DoctorListItemAdapter(
    private val listener: OnDoctorListener
) : ListAdapter<DoctorBySpecialtyResponse.Data, DoctorListItemAdapter.DoctorBySpecialtyViewHolder>(
    ExampleListDiffUtil()
) {
    inner class DoctorBySpecialtyViewHolder(private val binding: ItemBookScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(doctor: DoctorBySpecialtyResponse.Data) {
            binding.apply {
                tvAddress.text = doctor.addressTest
                tvSpecialty.text = doctor.specialty.name
                tvNameDoctor.text = doctor.name
                Glide.with(ivSpecialty).load(doctor.specialty.image).centerCrop()
                    .placeholder(R.drawable.ic_hepatitis_transmission_black)
                    .into(ivSpecialty)
                Glide.with(imvAvt).load(doctor.avatar).centerCrop()
                    .placeholder(R.drawable.img_default_avatar_home)
                    .into(imvAvt)
                root.setOnClickListener {
                    listener.getDoctor(doctor.id)
                }
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<DoctorBySpecialtyResponse.Data>() {
        override fun areContentsTheSame(oldItem: DoctorBySpecialtyResponse.Data, newItem: DoctorBySpecialtyResponse.Data) =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: DoctorBySpecialtyResponse.Data, newItem: DoctorBySpecialtyResponse.Data) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorBySpecialtyViewHolder {
        val binding =
            ItemBookScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorBySpecialtyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorBySpecialtyViewHolder, position: Int) {
        val okRs = getItem(position)
        holder.bind(okRs)
    }
}

interface OnDoctorListener {
    fun getDoctor(id: Int)
}