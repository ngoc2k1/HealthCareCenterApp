package com.example.myapplication.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemWorkScheduleDoctorBinding
import com.example.myapplication.doctor.OnMedicalHistoryClick
import com.example.myapplication.model.BookScheduleByPatientResponse
import com.example.myapplication.utils.convertStatusBook
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.visible

class ListBookSchedulePatientAdapter(
    private val listener: OnDetailSchedule

) : ListAdapter<BookScheduleByPatientResponse.Data, ListBookSchedulePatientAdapter.BookVH>(
    ExampleListDiffUtil()
) {
    inner class BookVH(private val binding: ItemWorkScheduleDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookScheduleByPatientResponse.Data) {
            binding.apply {
                book.doctor.apply {
                    tvUserName.text = name
                    tvAgeGender.text = specialty.name
                    Glide.with(imvAvatarWorkSchedule).load(book.doctor.avatar)
                        .centerCrop()
                        .placeholder(R.drawable.img_default_avatar_home)
                        .into(imvAvatarWorkSchedule)
                }
                val mTime = book.timeTest
                val first = mTime.split("-")[0].split(":")[0].trim().toInt()
                val second = mTime.split("-")[1].split(":")[0].trim().toInt()
                if (first < second) tvTime.text = book.dateTest + " | "+"${mTime.split("-")[0]}-${mTime.split("-")[1]}"
                else tvTime.text = book.dateTest + " | "+"${mTime.split("-")[1]}-${mTime.split("-")[0]}"
                root.setOnClickListener {
                    listener.getDetailSchedule(book.id)
                }
                val statusBook = convertStatusBook(book.statusBook, tvStatus)
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
                    }
                }
            }
        }
    }


    class ExampleListDiffUtil :
        DiffUtil.ItemCallback<BookScheduleByPatientResponse.Data>() {
        override fun areContentsTheSame(
            oldItem: BookScheduleByPatientResponse.Data,
            newItem: BookScheduleByPatientResponse.Data
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: BookScheduleByPatientResponse.Data,
            newItem: BookScheduleByPatientResponse.Data
        ) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookVH {
        val binding =
            ItemWorkScheduleDoctorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return BookVH(binding)
    }

    override fun onBindViewHolder(holder: BookVH, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }
}

interface OnDetailSchedule {
    fun getDetailSchedule(id: Int)
}