package com.a702.finafan.presentation.funding.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.a702.finafan.domain.funding.model.UsageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SubmitFormViewModel @Inject constructor(
) : ViewModel() {

    private val _submitFormState = MutableStateFlow(SubmitFormState())
    val submitFormState: StateFlow<SubmitFormState> = _submitFormState

    fun updateContent(content: String) {
        _submitFormState.update { it.copy(content = content) }
        Log.d("_submitFormState", _submitFormState.value.content)
    }

    fun updateUsageList(usageList: List<Pair<String, String>>) {
        val usageItemList = usageList.mapNotNull { (content, amountStr) ->
            val cleanAmount = amountStr.replace(",", "").replace("원", "")
            val amount = cleanAmount.toLongOrNull()
            if (amount != null) UsageItem(content, amount) else null
        }
        _submitFormState.update { it.copy(usageList = usageItemList) }
        Log.d("_submitFormState", "${_submitFormState.value.usageList}")
    }

    fun updateImageList(imageUris: List<Uri>) {
        _submitFormState.update { it.copy(imageList = imageUris.map { it.toString() }) }
        Log.d("_submitFormState", "${_submitFormState.value.imageList}")
    }
}