package com.a702.finafan.common.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.util.Locale

class SpeechRecognizerHelper(context: Context) {

    private val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private var onResult: (String) -> Unit = {}
    private var onError : (Int) -> Unit = {}
    private var onNoResult: () -> Unit = {}

    private val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN)
    }

    init {
        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val text = results
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.firstOrNull()

                if (text != null) onResult(text) else onNoResult()
            }

            override fun onError(error: Int) {onError(error)}
            override fun onEndOfSpeech() {}
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    /* ---------- public API ---------- */
    fun start()  = recognizer.startListening(intent)
    fun stop()   = recognizer.stopListening()
    fun cancel() = recognizer.cancel()
    fun destroy()= recognizer.destroy()

    fun setOnResult(block: (String) -> Unit) { onResult = block }
    fun setOnError (block: (Int) -> Unit)    { onError  = block }
    fun setOnNoResult(block: () -> Unit)     { onNoResult = block }
}

