package com.a702.finafan.common.utils

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class SpeechRecognizerManager(
    context: Context
) : DefaultLifecycleObserver {

    private val helper = SpeechRecognizerHelper(context)

    fun start(
        onResult: (String) -> Unit,
        onErrorToast: (String) -> Unit
    ) {
        helper.apply {
            setOnResult { text ->
                onResult(text)
            }
            setOnError  { _ ->
                onErrorToast("음성 인식 중 오류가 발생했어요.")
            }
            setOnNoResult {
                onErrorToast("말씀을 잘 못 알아들었어요.")
            }
            start()
        }
    }

    fun cancel() = helper.cancel()

    /* ---------- lifecycle hook ---------- */
    override fun onStop(owner: LifecycleOwner)   = cancel()
    override fun onDestroy(owner: LifecycleOwner){
        cancel()
        helper.destroy()
    }
}
