package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class NotificationModel(
    @SerializedName("aspecRatio")
    val aspecRatio: String,
    @SerializedName("cate")
    val cate: String,
    @SerializedName("cateId")
    val cateId: Int,
    @SerializedName("channel")
    val channelId: Int,
    @SerializedName("hashId")
    val hashId: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageSmall")
    val imageSmall: String,
    @SerializedName("imageThumb")
    val imageThumb: String,//anh dai dien video
    @SerializedName("isAdaptive")
    val isAdaptive: Int,//
    @SerializedName("isLike")
    val isLike: Int,//
    @SerializedName("list_resolution")
    val publishTime: Long,
    @SerializedName("resolution")
    val resolution: Int,//
    @SerializedName("status")
    val status: Int,//
    @SerializedName("totalComments")
    val totalComments: Int,
    @SerializedName("totalLikes")
    val totalLikes: Int,//dislike
    @SerializedName("totalShares")
    val totalShares: Int,//?
    @SerializedName("totalViews")
    val totalViews: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("videoDesc")
    val videoDesc: String,//
    @SerializedName("videoImage")
    val videoImage: String,//
    @SerializedName("videoMedia")
    val videoMedia: String,//link xem video
    @SerializedName("videoTime")
    val videoTime: String,//
    @SerializedName("videoTitle")
    val videoTitle: String
) : java.io.Serializable
