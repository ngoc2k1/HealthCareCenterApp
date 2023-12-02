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
                tvDate.text =
                    "Thời gian khám: " + notification.bookSchedule.datTest + " | " + notification.bookSchedule.timeTest
                tvTitle.text = notification.content
                tvPatient.text =
                    "Thông tin: " + notification.doctor.name + " | " + notification.doctor.specialty
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
