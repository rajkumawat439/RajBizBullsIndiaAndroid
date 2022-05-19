package com.dharmesh.bizzbullindiaproject.api

import com.dharmesh.bizzbullindiaproject.model.CommonResponse
import com.dharmesh.bizzbullindiaproject.model.FoFranchiseRequest
import com.dharmesh.bizzbullindiaproject.model.FoHealthRegistrationRequest
import com.dharmesh.bizzbullindiaproject.model.FoSocialDetailsRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface FoRegistrationInterface {

    @POST("fohealth")
    suspend fun submitHealthDetail(@Body franchiseeRegistrationRequest: FoHealthRegistrationRequest): CommonResponse

    @POST("fofranchise")
    suspend fun submitFoFranchiseDetails(@Body foFranchiseRequest: FoFranchiseRequest): CommonResponse

    @POST("fosocial")
    suspend fun submitFoSocialDetails(@Body foSocialDetailsRequest: FoSocialDetailsRequest): CommonResponse
}