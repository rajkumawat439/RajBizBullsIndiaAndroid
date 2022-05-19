package com.dharmesh.bizzbullindiaproject.model

class LoginResult {
    var error:String = ""
    var success:String = ""

    constructor(error: String, success: String) {
        this.error = error
        this.success = success
    }

    constructor()
}