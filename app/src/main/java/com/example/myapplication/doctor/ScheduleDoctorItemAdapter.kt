package com.example.myapplication.doctor


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.model.PatientModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemWorkScheduleDoctorBinding
import java.util.*


class ScheduleDoctorItemAdapter(
    private val context: Context//luon viet context o dau tham so
) : RecyclerView.Adapter<ScheduleDoctorItemAdapter.PatientVH>() {

    private var scheduleList: List<PatientModel> = ArrayList()//khoi tao = rong

    var onClickListener: OnItemClickListener? = null

    class PatientVH(
        val binding: ItemWorkScheduleDoctorBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientVH {
        return PatientVH(
            ItemWorkScheduleDoctorBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PatientVH, position: Int) {
        val schedule = scheduleList[position]
        holder.binding.apply {
            tvTime.text = schedule.videoTitle
            tvStatus.text = schedule.videoTitle
            tvUserName.text = schedule.videoTitle
            tvSpecialty.text = schedule.videoTitle
            tvAgeGender.text = schedule.videoTitle
            Glide.with(context).load(schedule.videoImage).centerCrop()
                .placeholder(R.drawable.img_default_avatar_home)
                .into(holder.binding.imvAvatarWorkSchedule)
            Glide.with(context).load(schedule.videoImage).centerCrop()
                .placeholder(R.drawable.img_default_avatar_home)
                .into(holder.binding.imvHepatitisTransmission)
            root.setOnClickListener {
                onClickListener?.onItemClick(schedule)
            }
        }
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(newData: List<PatientModel>) {
        this.scheduleList = newData
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(itemData: PatientModel)
    }
}