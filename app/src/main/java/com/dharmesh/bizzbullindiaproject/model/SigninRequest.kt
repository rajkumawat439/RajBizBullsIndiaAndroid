package com.dharmesh.bizzbullindiaproject.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SigninRequest(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("password")
    val password: String
) : Parcelable