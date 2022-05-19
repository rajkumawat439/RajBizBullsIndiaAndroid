package com.dharmesh.bizzbullindiaproject.repo

import com.dharmesh.bizzbullindiaproject.model.LoginResult

class LoginRepository {
    fun doLogin() : LoginResult {
        return LoginResult("success","success")
    }
}