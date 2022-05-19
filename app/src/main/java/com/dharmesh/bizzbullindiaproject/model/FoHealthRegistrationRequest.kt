package com.dharmesh.bizzbullindiaproject.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class FoHealthRegistrationRequest(
    @SerializedName("hdetail")
    val healthDetail: Hdetail,
    @SerializedName("task")
    val task: String,
    @SerializedName("userid")
    val userid: String
) : Parcelable {
    @Parcelize
    data class Hdetail(
        @SerializedName("birthidentificationmarks")
        val birthidentificationmarks: String,
        @SerializedName("anyotherhealthissue")
        val anyotherhealthissue: String,
        @SerializedName("anyunhealthyhabit")
        val anyunhealthyhabit: String,
        @SerializedName("birthidentificationmarks2")
        val birthidentificationmarks2: String,
        @SerializedName("bloodgroup")
        val bloodgroup: String,
        @SerializedName("habbitdetails")
        val habbitdetails: String,
        @SerializedName("handuse")
        val handuse: String,
        @SerializedName("height")
        val height: String,
        @SerializedName("otherissuesdetail")
        val otherissuesdetail: String,
        @SerializedName("physycalhandicape")
        val physycalhandicape: String,
        @SerializedName("surgelesstreatmentundergo")
        val surgelesstreatmentundergo: String,
        @SerializedName("typeofph")
        val typeofph: String,
        @SerializedName("typeofsurgery")
        val typeofsurgery: String,
        @SerializedName("willingtodonate")
        val willingtodonate: String,
        @SerializedName("weight")
        val weight: String,
        @SerializedName("userid")
        val userid: String
    ) : Parcelable
}