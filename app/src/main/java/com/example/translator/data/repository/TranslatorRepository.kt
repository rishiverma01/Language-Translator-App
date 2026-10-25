package com.example.translator.data.repository

import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class TranslatorRepository {

    suspend fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): String {

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()

        val translator = Translation.getClient(options)

        return suspendCancellableCoroutine<String> { continuation ->

            translator.downloadModelIfNeeded()

                .addOnSuccessListener {

                    translator.translate(text)

                        .addOnSuccessListener { result ->

                            continuation.resume(result)
                        }

                        .addOnFailureListener { exception ->

                            continuation.resume(
                                exception.message
                                    ?: "Translation Failed"
                            )
                        }
                }

                .addOnFailureListener { exception ->

                    continuation.resume(
                        exception.message
                            ?: "Model Download Failed"
                    )
                }
        }
    }
}