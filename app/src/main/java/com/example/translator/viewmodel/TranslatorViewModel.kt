package com.example.translator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.data.repository.TranslatorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TranslatorViewModel : ViewModel() {

    private val repository =
        TranslatorRepository()

    private val _translatedText =
        MutableStateFlow("")

    val translatedText:
            StateFlow<String>
        get() = _translatedText

    fun translate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ) {

        viewModelScope.launch {

            _translatedText.value =
                repository.translateText(
                    text,
                    sourceLanguage,
                    targetLanguage
                )
        }
    }
}