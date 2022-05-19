package com.dharmesh.bizzbullindiaproject.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharmesh.bizzbullindiaproject.model.CommonResponse
import com.dharmesh.bizzbullindiaproject.repo.SignupRepository
import kotlinx.coroutines.launch


class SignupViewModel(private var signupRepository: SignupRepository) : BaseViewModel(){
    private val _signupResult = MutableLiveData<CommonResponse>()
    val signupResult: LiveData<CommonResponse> = _signupResult

    fun signup(username: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            // can be launched in a separate asynchronous job
            safeCall {
                _signupResult.postValue(signupRepository.doSignup(username, password, confirmPassword))
            }
        }
    }

    fun validate(userId: String, password: String, confirmPassword: String): Boolean {
        if (userId.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _showToast.postValue("Fill all the field.")
            return false
        } else if (password.length < 8) {
            _showToast.postValue("Password must be 8 chars.")
            return false
        } else if (confirmPassword.length < 8) {
            _showToast.postValue("Confirm password must be 8 chars.")
            return false
        } else if (password != confirmPassword) {
            _showToast.postValue("Password and confirm password does not match.")
            return false
        }
        return true
    }
}