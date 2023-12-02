package com.example.myapplication.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemNotificationBinding
import com.example.myapplication.model.DoctorNotificationResponse
import com.example.myapplication.utils.convertGender

class DoctorListNotificationItemmAdapter(
) : ListAdapter<DoctorNotificationResponse.Data, DoctorListNotificationItemmAdapter.NotificationViewHolder>(
    ExampleListDiffUtil()
) {
    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: DoctorNotificationResponse.Data) {
            binding.apply {
                tvDate.text =
                    "Thời gian khám: " + notification.bookSchedule.datTest + " | " + notification.bookSchedule.timeTest
                tvTitle.text = notification.content
                tvPatient.text =
                    "Bệnh nhân: " + notification.patient.name + " | " + convertGender(
                        notification.patient.gender
                    ) + " | " + notification.patient.age + " tuổi"
//                tvState
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<DoctorNotificationResponse.Data>() {
        override fun areContentsTheSame(
            oldItem: DoctorNotificationResponse.Data,
            newItem: DoctorNotificationResponse.Data
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: DoctorNotificationResponse.Data,
            newItem: DoctorNotificationResponse.Data
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
