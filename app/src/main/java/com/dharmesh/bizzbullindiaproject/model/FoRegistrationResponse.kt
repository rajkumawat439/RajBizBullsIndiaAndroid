package com.dharmesh.bizzbullindiaproject.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoRegistrationResponse(
    @SerializedName("msg")
    val msg: List<Msg>
) : Parcelable {
    @Parcelize
    data class Msg(
        @SerializedName("anyotherhealthissue")
        val anyotherhealthissue: String?,
        @SerializedName("anyunhealthyhabit")
        val anyunhealthyhabit: String?,
        @SerializedName("birthidentificationmarks")
        val birthidentificationmarks: String,
        @SerializedName("birthidentificationmarks2")
        val birthidentificationmarks2: String?,
        @SerializedName("bloodgroup")
        val bloodgroup: String?,
        @SerializedName("habbitdetails")
        val habbitdetails: String?,
        @SerializedName("handuse")
        val handuse: String?,
        @SerializedName("height")
        val height: String?,
        @SerializedName("otherissuesdetail")
        val otherissuesdetail: String?,
        @SerializedName("physycalhandicape")
        val physycalhandicape: String?,
        @SerializedName("surgelesstreatmentundergo")
        val surgelesstreatmentundergo: String?,
        @SerializedName("typeofph")
        val typeofph: String?,
        @SerializedName("typeofsurgery")
        val typeofsurgery: String?,
        @SerializedName("userid")
        val userid: String,
        @SerializedName("weight")
        val weight: String?,
        @SerializedName("willingtodonate")
        val willingtodonate: String?
    ) : Parcelable
}