package com.example.myapplication.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemNotificationBinding
import com.example.myapplication.model.PatientNotificationResponse
import com.example.myapplication.utils.convertGender

class PatientListNotificationItemmAdapter(
) : ListAdapter<PatientNotificationResponse.Data, PatientListNotificationItemmAdapter.NotificationViewHolder>(
    ExampleListDiffUtil()
) {
    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: PatientNotificationResponse.Data) {
            binding.apply {
                var time = ""
                val mTime = notification.bookSchedule.timeTest
                val first = mTime.split("-")[0].split(":")[0].trim().toInt()
                val second = mTime.split("-")[1].split(":")[0].trim().toInt()
                time = if (first < second) "${mTime.split("-")[0]}-${mTime.split("-")[1]}"
                else "${mTime.split("-")[1]}-${mTime.split("-")[0]}"
                tvDate.text =
                    "Thời gian khám: " + notification.bookSchedule.datTest + " | " + time
                tvTitle.text = notification.content
                tvPatient.text =
                    "Thông tin bác sĩ: " + notification.doctor.name + " | " + notification.doctor.specialty.name
//                tvState
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<PatientNotificationResponse.Data>() {
        override fun areContentsTheSame(
            oldItem: PatientNotificationResponse.Data,
            newItem: PatientNotificationResponse.Data
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: PatientNotificationResponse.Data,
            newItem: PatientNotificationResponse.Data
        ) =
            oldItem.bookSchedule.id == newItem.bookSchedule.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = getItem(position)
        holder.bind(notification)
    }
}
