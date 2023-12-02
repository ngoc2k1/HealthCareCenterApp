package com.example.myapplication.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemWorkScheduleDoctorBinding
import com.example.myapplication.model.WorkScheduleResponse
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.convertGender
import com.example.myapplication.utils.convertStatusBook
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.visible

class ScheduleDoctorItemmAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<WorkScheduleResponse.WorkSchedule, ScheduleDoctorItemmAdapter.ScheduleViewHolder>(
    ExampleListDiffUtil()
) {
    inner class ScheduleViewHolder(private val binding: ItemWorkScheduleDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: WorkScheduleResponse.WorkSchedule) {
            binding.apply {
                tvUserName.text = schedule.patient.name

                tvAgeGender.text =
                    convertGender(schedule.patient.gender) + ", " + schedule.patient.age + " tuổi"
                tvTime.text = schedule.dateTest + " | " + schedule.timeTest
                val statusBook = convertStatusBook(schedule.statusBook, tvStatus)
                tvStatus.text = statusBook

                when (statusBook) {
                    tvStatus.context.getString(R.string.str_chuakham) -> {
                        viewCancel.gone()
                        tvStatus.setBackgroundDrawable(tvStatus.resources.getDrawable(R.drawable.bg_item_work_state_notest))
                    }

                    tvStatus.context.getString(R.string.str_dakham) -> {
                        viewCancel.gone()
                        tvStatus.setBackgroundDrawable(tvStatus.resources.getDrawable(R.drawable.bg_item_work_state_tested))
                    }

                    else -> {
                        viewCancel.visible()
                        tvStatus.setBackgroundDrawable(tvStatus.resources.getDrawable(R.drawable.bg_item_work_state_cancelled))
                    }
                }
                Glide.with(imvAvatarWorkSchedule).load(schedule.patient.avatar).centerCrop()
                    .placeholder(R.drawable.img_default_avatar_home)
                    .into(imvAvatarWorkSchedule)
                root.setOnClickListener {
//                    listener.getDetailPatient(schedule.id)
                }
            }

        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<WorkScheduleResponse.WorkSchedule>() {
        override fun areContentsTheSame(
            oldItem: WorkScheduleResponse.WorkSchedule,
            newItem: WorkScheduleResponse.WorkSchedule
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: WorkScheduleResponse.WorkSchedule,
            newItem: WorkScheduleResponse.WorkSchedule
        ) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemWorkScheduleDoctorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = getItem(position)
        holder.bind(schedule)
    }
}

interface OnItemClickListener {
    fun getDetailSchedule(id: Int)
}