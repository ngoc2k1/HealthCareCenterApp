package com.example.myapplication.doctor


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.NotificationModel
import com.example.myapplication.databinding.ItemNotificationBinding
import java.util.*


class NotificationItemAdapter(
    private val context: Context//luon viet context o dau tham so
) : RecyclerView.Adapter<NotificationItemAdapter.NotificationVH>() {

    private var notificationList: List<NotificationModel> = ArrayList()//khoi tao = rong

    var onClickListener: OnItemClickListener? = null

    class NotificationVH(
        val binding: ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVH {
        return NotificationVH(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationVH, position: Int) {
        val patient = notificationList[position]
        holder.binding.apply {
            tvContent.text = patient.videoTitle
            tvDaytime.text = patient.videoTitle
            tvState.text = patient.videoTitle
            tvTitle.text = patient.videoTitle
        }
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(newData: List<NotificationModel>) {
        this.notificationList = newData
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(itemData: NotificationModel)
    }
}