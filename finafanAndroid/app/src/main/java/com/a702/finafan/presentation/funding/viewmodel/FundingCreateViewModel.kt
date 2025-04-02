package com.a702.finafan.presentation.funding.viewmodel

import androidx.lifecycle.ViewModel
import com.a702.finafan.domain.funding.repository.FundingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FundingCreateViewModel @Inject constructor(
    private val repository: FundingRepository
): ViewModel() {

}