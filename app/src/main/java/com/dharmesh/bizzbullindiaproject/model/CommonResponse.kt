package com.dharmesh.bizzbullindiaproject.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CommonResponse(
    @SerializedName("msg")
    val msg: String
) : Parcelable