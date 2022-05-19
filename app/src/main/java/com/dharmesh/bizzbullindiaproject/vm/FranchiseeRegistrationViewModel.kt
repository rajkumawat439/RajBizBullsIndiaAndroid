package com.dharmesh.bizzbullindiaproject.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dharmesh.bizzbullindiaproject.model.CommonResponse
import com.dharmesh.bizzbullindiaproject.model.FoFranchiseRequest
import com.dharmesh.bizzbullindiaproject.model.FoHealthRegistrationRequest
import com.dharmesh.bizzbullindiaproject.model.FoSocialDetailsRequest
import com.dharmesh.bizzbullindiaproject.repo.FranchiseeRegistrationRepository
import kotlinx.coroutines.launch

class FranchiseeRegistrationViewModel(private var franchiseeRegistrationRepository: FranchiseeRegistrationRepository) :
    BaseViewModel() {

    private val _foRegistrationResult = MutableLiveData<CommonResponse>()
    val foRegistrationResult: LiveData<CommonResponse> = _foRegistrationResult

    fun submitHealthDetail(franchiseeRegistrationRequest: FoHealthRegistrationRequest) {
        viewModelScope.launch {
            safeCall {
                _foRegistrationResult.postValue(
                    franchiseeRegistrationRepository.submitHealthDetail(
                        franchiseeRegistrationRequest
                    )
                )
            }
        }
    }

    fun submitFoFranchiseDetails(foFranchiseRequest: FoFranchiseRequest) {
        viewModelScope.launch {
            safeCall {
                _foRegistrationResult.postValue(
                    franchiseeRegistrationRepository.submitFoFranchiseDetails(
                        foFranchiseRequest
                    )
                )
            }
        }
    }

    fun submitFoSocialDetails(foSocialDetailsRequest: FoSocialDetailsRequest) {
        viewModelScope.launch {
            safeCall {
                _foRegistrationResult.postValue(
                    franchiseeRegistrationRepository.submitFoSocialDetails(
                        foSocialDetailsRequest
                    )
                )
            }
        }
    }

}