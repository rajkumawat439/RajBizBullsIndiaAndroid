package com.dharmesh.bizzbullindiaproject.vm

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharmesh.bizzbullindiaproject.R
import com.dharmesh.bizzbullindiaproject.model.CommonResponse
import com.dharmesh.bizzbullindiaproject.repo.SignupRepository
import kotlinx.coroutines.launch

class LoginViewModel(private var loginrepo: SignupRepository) : BaseViewModel() {

    private val _signinresult = MutableLiveData<CommonResponse>()
    val loginresult: LiveData<CommonResponse> = _signinresult

    fun signin(username: String, password: String) {
        viewModelScope.launch {
            safeCall {
                _signinresult.postValue(loginrepo.doSignin(username, password))
            }
        }
    }



    fun validate(userId: String, password: String): Boolean {
        if (userId.isBlank() || password.isBlank()) {
            _showToast.postValue("Fill all the field.")
            return false
        } else if (password.length < 8) {
            _showToast.postValue("Password must be 8 chars.")
            return false
        }
        return true
    }
}