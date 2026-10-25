package com.example.translator.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TTSManager(
    context: Context
) {

    private var tts: TextToSpeech? =
        TextToSpeech(
            context
        ) {

        }

    fun speak(
        text: String
    ) {

        tts?.language =
            Locale.getDefault()

        tts?.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            null
        )
    }

    fun shutdown() {

        tts?.stop()

        tts?.shutdown()
    }
}