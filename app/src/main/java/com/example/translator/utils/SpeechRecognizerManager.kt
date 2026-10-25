package com.example.translator.utils

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent

class SpeechRecognizerManager {

    fun getSpeechIntent(): Intent {

        return Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }
    }
}