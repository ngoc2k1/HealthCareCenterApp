package com.example.myapplication.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemMedicalHistoryBinding
import com.example.myapplication.model.MedicalHistoryListDoctorResponse

class MedicalHistoryListPatientItemmAdapter(
    private val listener: OnMedicalHistoryClick

) : ListAdapter<MedicalHistoryListDoctorResponse.Data, MedicalHistoryListPatientItemmAdapter.MedicalHistoryVH>(
    ExampleListDiffUtil()
) {
    inner class MedicalHistoryVH(private val binding: ItemMedicalHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(medicalHistory: MedicalHistoryListDoctorResponse.Data) {
            binding.apply {
//                groupDoctor.gone()
                root.setOnClickListener {
                    listener.getDetailMedicalHistory(medicalHistory.id)
                }
                tvTime.text = medicalHistory.bookSchedule.dateTest
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<MedicalHistoryListDoctorResponse.Data>() {
        override fun areContentsTheSame(
            oldItem: MedicalHistoryListDoctorResponse.Data,
            newItem: MedicalHistoryListDoctorResponse.Data
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: MedicalHistoryListDoctorResponse.Data,
            newItem: MedicalHistoryListDoctorResponse.Data
        ) =
            oldItem.bookSchedule.id == newItem.bookSchedule.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalHistoryVH {
        val binding =
            ItemMedicalHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicalHistoryVH(binding)
    }

    override fun onBindViewHolder(holder: MedicalHistoryVH, position: Int) {
        val medicalHistory = getItem(position)
        holder.bind(medicalHistory)
    }
}

interface OnMedicalHistoryClick {
    fun getDetailMedicalHistory(id: Int)
}