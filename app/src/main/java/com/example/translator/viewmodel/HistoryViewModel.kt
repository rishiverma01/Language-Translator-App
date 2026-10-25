package com.example.translator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.data.db.TranslationHistory
import com.example.translator.data.repository.HistoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: HistoryRepository
) : ViewModel() {

    val historyList =
        repository.historyList
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun saveHistory(
        sourceText: String,
        translatedText: String,
        sourceLanguage: String,
        targetLanguage: String
    ) {

        viewModelScope.launch {

            repository.insertHistory(
                TranslationHistory(
                    sourceText = sourceText,
                    translatedText = translatedText,
                    sourceLanguage = sourceLanguage,
                    targetLanguage = targetLanguage,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun clearHistory() {

        viewModelScope.launch {

            repository.clearHistory()
        }
    }
}