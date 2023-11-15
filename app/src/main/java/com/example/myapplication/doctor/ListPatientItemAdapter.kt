package com.example.myapplication.doctor


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.model.patient.PatientModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemPatientBinding
import java.util.*


class ListPatientItemAdapter(
    private val context: Context//luon viet context o dau tham so
) : RecyclerView.Adapter<ListPatientItemAdapter.PatientVH>() {

    private var dataVideoList: List<PatientModel> = ArrayList()//khoi tao = rong

    var onClickListener: OnItemClickListener? = null

    class PatientVH(
        val binding: ItemPatientBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientVH {
        return PatientVH(
            ItemPatientBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PatientVH, position: Int) {
        val patient = dataVideoList[position]
        holder.binding.apply {
            tvName.text = patient.id.toString()
            tvSexAge.text = patient.id.toString() + ", " + patient.id.toString()
//        val millisecondsTodayDate = Date().time
//        val millisecondsPeriod = millisecondsTodayDate - video.publishTime
//        holder.binding.tvTime.text = convertMillisecondsToDate(millisecondsPeriod)
//        holder.binding.tvItemvideoTotalviews.text = video.totalViews.toString() + " views"
//        Glide.with(context).load(video.channelAvatar).centerCrop()
//            .placeholder(R.drawable.background)
//            .into(holder.binding.ivItemvideoChannelavatar);

            Glide.with(context).load(patient.id.toString()).centerCrop()
                .placeholder(R.drawable.img_default_avatar_home)
                .into(holder.binding.imvAvatar)

            root.setOnClickListener {
                onClickListener?.onItemClick(patient)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataVideoList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(newData: List<PatientModel>) {
        this.dataVideoList = newData
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(itemData: PatientModel)
    }
}