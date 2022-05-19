package com.dharmesh.bizzbullindiaproject.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class FoFranchiseRequest(
    @SerializedName("franchdetails")
    val franchiseDetails: FranchiseDetails,
    @SerializedName("task")
    val task: String,
    @SerializedName("userid")
    val userid: String
) : Parcelable {
    @Parcelize
    data class FranchiseDetails(
        @SerializedName("interestedpojectname")
        val interestedProjectName: String?=null,
        @SerializedName("industryofproject")
        val industryofproject: String?=null,
        @SerializedName("locationofinterest")
        val locationofinterest: String,
        @SerializedName("financialassistance")
        val financialassistance: String,
        @SerializedName("registrationfee")
        val registrationfee: String?=null,
        @SerializedName("franchiseplaningfor")
        val franchiseplaningfor: String,
        @SerializedName("franchiseplaingas")
        val franchisePlanningAs: String,
        @SerializedName("locationofintersst2")
        val locationofintersst2: String?=null,
        @SerializedName("businessplacetype")
        val businessplacetype: String,
        @SerializedName("businessplacesize")
        val businessplacesize: String,
        @SerializedName("userid")
        val userid: String
    ) : Parcelable
}