package com.dharmesh.bizzbullindiaproject.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dharmesh.bizzbullindiaproject.api.ApiClient
import com.dharmesh.bizzbullindiaproject.api.FoRegistrationInterface
import com.dharmesh.bizzbullindiaproject.api.SignupInterface
import com.dharmesh.bizzbullindiaproject.repo.FranchiseeRegistrationRepository
import com.dharmesh.bizzbullindiaproject.repo.SignupRepository
import com.dharmesh.bizzbullindiaproject.vm.FranchiseeRegistrationViewModel
import com.dharmesh.bizzbullindiaproject.vm.LoginViewModel
import com.dharmesh.bizzbullindiaproject.vm.SignupViewModel

class LoginViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginrepo = SignupRepository(
                    ApiClient.retrofitAuth().create(SignupInterface::class.java)
                )
            ) as T
        } else if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(
                signupRepository = SignupRepository(
                    ApiClient.retrofitAuth().create(SignupInterface::class.java)
                )
            ) as T
        } else if (modelClass.isAssignableFrom(FranchiseeRegistrationViewModel::class.java)) {
            return FranchiseeRegistrationViewModel(
                franchiseeRegistrationRepository = FranchiseeRegistrationRepository(
                    ApiClient.retrofitAuth().create(FoRegistrationInterface::class.java)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}