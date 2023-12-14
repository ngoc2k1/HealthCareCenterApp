package com.example.myapplication.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemSpecialtyBinding
import com.example.myapplication.model.SpecialtyUI

class SpecialtyListItemAdapter(
    private val listener: OnSpecialtyListener
) : ListAdapter<SpecialtyUI, SpecialtyListItemAdapter.SpecialtyViewHolder>(
    ExampleListDiffUtil()
) {
    inner class SpecialtyViewHolder(private val binding: ItemSpecialtyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(specialty: SpecialtyUI) {
            binding.apply {
                tvName.text = specialty.name
                if (specialty.isClicked) {
                    tvName.setBackgroundResource(R.drawable.bg_item_selected_specialty)
                } else {
                    tvName.setBackgroundResource(R.drawable.bg_border_form)
                }
                root.setOnClickListener {
                    listener.getSpecialty(specialty,layoutPosition)
                }
            }
        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<SpecialtyUI>() {
        override fun areContentsTheSame(
            oldItem: SpecialtyUI,
            newItem: SpecialtyUI
        ) =
            oldItem == newItem

        override fun areItemsTheSame(
            oldItem: SpecialtyUI,
            newItem: SpecialtyUI
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
    fun getSpecialty(specialty: SpecialtyUI, index: Int)
}