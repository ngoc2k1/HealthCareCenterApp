package com.example.myapplication.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemTimeBinding
import com.example.myapplication.model.Specialty
import com.example.myapplication.model.TimeByDoctorResponse
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.visible

class TimeListItemAdapter(
    private val listener: OnTimeListener
) : ListAdapter<TimeByDoctorResponse.Data, TimeListItemAdapter.SpecialtyViewHolder>(
    ExampleListDiffUtil()
) {
    inner class SpecialtyViewHolder(private val binding: ItemTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(time: TimeByDoctorResponse.Data) {
            binding.apply {
                tvName.text = time.value
                tvPrice.text = time.price.toString()

                if (!time.isBooked) {
                    tvName.apply {
                        setBackgroundResource(R.drawable.bg_item_unselected)
                        setTextColor(resources.getColor(R.color.black))
                    }
                    tvPrice.visible()
                    root.setOnClickListener {
                        tvName.apply {
                            setBackgroundResource(R.drawable.bg_item_selected)
                            setTextColor(resources.getColor(R.color.white))
                        }
                        listener.getTime(time.id)
                    }
                } else {
                    tvName.gone()
                    tvPrice.gone()
                }
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<TimeByDoctorResponse.Data>() {
        override fun areContentsTheSame(
            oldItem: TimeByDoctorResponse.Data,
            newItem: TimeByDoctorResponse.Data
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: TimeByDoctorResponse.Data,
            newItem: TimeByDoctorResponse.Data
        ) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val binding =
            ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialtyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val okRs = getItem(position)
        holder.bind(okRs)
    }
}

interface OnTimeListener {
    fun getTime(doctorWorkScheduleId: Int)
}