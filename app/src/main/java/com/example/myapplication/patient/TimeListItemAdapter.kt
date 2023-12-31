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
import com.example.myapplication.model.TimeByDoctorUI
import com.example.myapplication.utils.gone
import com.example.myapplication.utils.visible

class TimeListItemAdapter(
    private val listener: OnTimeListener
) : ListAdapter<TimeByDoctorUI, TimeListItemAdapter.TimeVH>(
    ExampleListDiffUtil()
) {
    inner class TimeVH(private val binding: ItemTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(time: TimeByDoctorUI) {
            binding.apply {
                if (!time.isBooked) {
                    val mTime = time.value
                    val first = mTime.split("-")[0].split(":")[0].trim().toInt()
                    val second = mTime.split("-")[1].split(":")[0].trim().toInt()
                    if (first < second) tvName.text = "${mTime.split("-")[0]}-${mTime.split("-")[1]}"
                    else tvName.text = "${mTime.split("-")[1]}-${mTime.split("-")[0]}"
                    tvPrice.text = time.price.toString() + " đồng"
                    tvPrice.visible()
                    view.visible()
                    if (time.isClicked) {
                        container.setBackgroundResource(R.drawable.bg_item_selected_specialty)
                    } else {
                        container.setBackgroundResource(R.drawable.bg_item_unselected)
                    }
                    root.setOnClickListener {
                        listener.getTime(time.id, time)
                    }
                } else {
                    container.gone()
                }
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<TimeByDoctorUI>() {
        override fun areContentsTheSame(
            oldItem: TimeByDoctorUI,
            newItem: TimeByDoctorUI
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: TimeByDoctorUI,
            newItem: TimeByDoctorUI
        ) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeVH {
        val binding =
            ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeVH(binding)
    }

    override fun onBindViewHolder(holder: TimeVH, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

interface OnTimeListener {
    fun getTime(doctorWorkScheduleId: Int, time: TimeByDoctorUI)
}