package com.dharmesh.bizzbullindiaproject.repo

import com.dharmesh.bizzbullindiaproject.api.SignupInterface
import com.dharmesh.bizzbullindiaproject.model.SigninRequest
import com.dharmesh.bizzbullindiaproject.model.SignupRequest
import com.dharmesh.bizzbullindiaproject.model.CommonResponse

class SignupRepository(private val signupInterface: SignupInterface) {
    suspend fun doSignup(
        username: String,
        password: String,
        confirmPassword: String
    ): CommonResponse {
        return signupInterface.doSignup(SignupRequest(username, password, confirmPassword))
    }

    suspend fun doSignin(
        username: String,
        password: String
    ): CommonResponse {
        return signupInterface.doSignin(SigninRequest(username, password))
    }
}