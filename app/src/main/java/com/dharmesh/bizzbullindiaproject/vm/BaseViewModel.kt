package com.dharmesh.bizzbullindiaproject.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    internal val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> = _showToast

    suspend fun safeCall(method: suspend () -> Unit) {
        try {
            method()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}