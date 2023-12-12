package com.example.myapplication.patient

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemSpecialtyBinding
import com.example.myapplication.model.Specialty
import com.example.myapplication.model.SpecialtyData
import com.example.myapplication.model.SpecialtyListResponse

class SpecialtyListItemAdapter(
    private val listener: OnSpecialtyListener
) : ListAdapter<SpecialtyData, SpecialtyListItemAdapter.SpecialtyViewHolder>(
    ExampleListDiffUtil()
) {
    inner class SpecialtyViewHolder(private val binding: ItemSpecialtyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(specialty: SpecialtyData) {
            binding.apply {
                tvName.text = specialty.name
                if (specialty.isClicked) {
                    tvName.setBackgroundResource(R.drawable.bg_item_selected)
                } else {
                    tvName.setBackgroundResource(R.drawable.bg_item_unselected)
                }
                tvName.setOnClickListener {
                    listener.getSpecialty(specialty)
                }
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<SpecialtyData>() {
        override fun areContentsTheSame(
            oldItem: SpecialtyData,
            newItem: SpecialtyData
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: SpecialtyData,
            newItem: SpecialtyData
        ) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val binding =
            ItemSpecialtyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialtyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val specialty = getItem(position)
        holder.bind(specialty)
    }
}

interface OnSpecialtyListener {
    fun getSpecialty(specialty: SpecialtyData)
}