package com.example.myapplication.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemMedicalHistoryPatientBinding
import com.example.myapplication.doctor.OnMedicalHistoryClick
import com.example.myapplication.model.MedicalHistoryListPatientResponse

class ProfilePatienScheduleItemFullAdapter(
    private val listener: OnMedicalHistoryClick

) : ListAdapter<MedicalHistoryListPatientResponse.Data, ProfilePatienScheduleItemFullAdapter.MedicalHistoryVH>(
    ExampleListDiffUtil()
) {
    inner class MedicalHistoryVH(private val binding: ItemMedicalHistoryPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(medicalHistory: MedicalHistoryListPatientResponse.Data) {
            binding.apply {
                medicalHistory.bookSchedule.doctor.apply {
                    tvNameDoctor.text = name
                    tvAddress.text = addressTest
                    tvSpecialty.text = specialty.name
                    Glide.with(imgAvatar).load(medicalHistory.bookSchedule.doctor.avatar)
                        .centerCrop()
                        .placeholder(R.drawable.img_default_avatar_home)
                        .into(imgAvatar)
                    Glide.with(imgAvatar).load(specialty.image)
                        .centerCrop()
                        .placeholder(R.drawable.img_default_avatar_home)
                        .into(imgAvatar)
                }

                root.setOnClickListener {
                    listener.getDetailMedicalHistory(medicalHistory.id)
                }
                tvTime.text = medicalHistory.bookSchedule.dateTest
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<MedicalHistoryListPatientResponse.Data>() {
        override fun areContentsTheSame(
            oldItem: MedicalHistoryListPatientResponse.Data,
            newItem: MedicalHistoryListPatientResponse.Data
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: MedicalHistoryListPatientResponse.Data,
            newItem: MedicalHistoryListPatientResponse.Data
        ) =
            oldItem.bookSchedule.id == newItem.bookSchedule.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalHistoryVH {
        val binding =
            ItemMedicalHistoryPatientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MedicalHistoryVH(binding)
    }

    override fun onBindViewHolder(holder: MedicalHistoryVH, position: Int) {
        val medicalHistory = getItem(position)
        holder.bind(medicalHistory)
    }
}
