package com.example.myapplication.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemTimeBinding
import com.example.myapplication.model.DateByDoctorUI

class DateListItemAdapter(
    private val listener: OnDateListener
) : ListAdapter<DateByDoctorUI, DateListItemAdapter.DateViewHolder>(
    ExampleListDiffUtil()
) {

    inner class DateViewHolder(private val binding: ItemTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DateByDoctorUI) {
            binding.apply {
                tvName.text = data.date
                if (data.isClicked) {
                    tvName.setBackgroundResource(R.drawable.bg_item_selected_specialty)
                } else {
                    tvName.setBackgroundResource(R.drawable.bg_item_unselected)
                }
                root.setOnClickListener {
                    listener.getDate(data)
                }
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<DateByDoctorUI>() {
        override fun areContentsTheSame(
            oldItem: DateByDoctorUI,
            newItem: DateByDoctorUI
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: DateByDoctorUI,
            newItem: DateByDoctorUI
        ) =
            oldItem.date == newItem.date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding =
            ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

interface OnDateListener {
    fun getDate(date: DateByDoctorUI)
}