package com.dharmesh.bizzbullindiaproject.api

import com.dharmesh.bizzbullindiaproject.model.CommonResponse
import com.dharmesh.bizzbullindiaproject.model.SigninRequest
import com.dharmesh.bizzbullindiaproject.model.SignupRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupInterface {

    @POST("UserAuth")
    suspend fun doSignup(@Body signupRequest: SignupRequest): CommonResponse

    @POST("Login")
    suspend fun doSignin(@Body signupRequest: SigninRequest): CommonResponse

}