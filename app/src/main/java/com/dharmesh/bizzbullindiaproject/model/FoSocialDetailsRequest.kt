package com.dharmesh.bizzbullindiaproject.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoSocialDetailsRequest(
    @SerializedName("socialdetail")
    val foSocialDetails: FoSocialDetails,
    @SerializedName("task")
    val task: String,
    @SerializedName("userid")
    val userid: String
) : Parcelable {
    @Parcelize
    data class FoSocialDetails(
        @SerializedName("adharcardnumber")
        val adharcardnumber: String? = null,
        @SerializedName("pancardno")
        val pancardno: String? = null,
        @SerializedName("drivinglicenseno")
        val drivinglicenseno: String,
        @SerializedName("passportno")
        val passportno: String,
        @SerializedName("votteridno")
        val votteridno: String? = null,
        @SerializedName("rationcardno")
        val rationcardno: String,
        @SerializedName("userid")
        val userid: String
    ) : Parcelable
}