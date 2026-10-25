package com.example.translator.data.repository

import com.example.translator.data.db.HistoryDao
import com.example.translator.data.db.TranslationHistory

class HistoryRepository(
    private val dao: HistoryDao
) {

    val historyList =
        dao.getAllHistory()

    suspend fun insertHistory(
        history: TranslationHistory
    ) {

        dao.insertHistory(history)
    }

    suspend fun clearHistory() {

        dao.clearHistory()
    }
}