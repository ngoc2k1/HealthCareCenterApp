package com.example.myapplication.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemPatientBinding
import com.example.myapplication.model.PatientProfile
import com.example.myapplication.utils.GENDER
import com.example.myapplication.utils.convertGender

class ListPatientItemmAdapter(
    private val listener: OnItemmClickListener
) : ListAdapter<PatientProfile, ListPatientItemmAdapter.PatientViewHolder>(
    ExampleListDiffUtil()
) {
    inner class PatientViewHolder(private val binding: ItemPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: PatientProfile) {
            binding.apply {
                tvName.text = patient.name
                tvSexAge.text =
                    convertGender(patient.gender) + ", " + patient.age.toString() + " tuổi"
                Glide.with(imgAvatar).load(patient.avatar).centerCrop()
                    .placeholder(R.drawable.img_default_avatar_home)
                    .into(imgAvatar);

                root.setOnClickListener {
                    listener.getDetailPatient(patient.id)
                }
            }

        }
    }

    class ExampleListDiffUtil : DiffUtil.ItemCallback<PatientProfile>() {
        override fun areContentsTheSame(oldItem: PatientProfile, newItem: PatientProfile) =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: PatientProfile, newItem: PatientProfile) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

interface OnItemmClickListener {
    fun getDetailPatient(id: Int)
}