package com.dharmesh.bizzbullindiaproject.repo

import com.dharmesh.bizzbullindiaproject.api.FoRegistrationInterface
import com.dharmesh.bizzbullindiaproject.model.CommonResponse
import com.dharmesh.bizzbullindiaproject.model.FoFranchiseRequest
import com.dharmesh.bizzbullindiaproject.model.FoHealthRegistrationRequest
import com.dharmesh.bizzbullindiaproject.model.FoSocialDetailsRequest

class FranchiseeRegistrationRepository(private val foRegistrationInterface: FoRegistrationInterface) {

    suspend fun submitHealthDetail(franchiseeRegistrationRequest: FoHealthRegistrationRequest): CommonResponse {
        return foRegistrationInterface.submitHealthDetail(franchiseeRegistrationRequest)
    }

    suspend fun submitFoFranchiseDetails(foFranchiseRequest: FoFranchiseRequest): CommonResponse {
        return foRegistrationInterface.submitFoFranchiseDetails(foFranchiseRequest)
    }

    suspend fun submitFoSocialDetails(foSocialDetailsRequest: FoSocialDetailsRequest): CommonResponse {
        return foRegistrationInterface.submitFoSocialDetails(foSocialDetailsRequest)
    }
}