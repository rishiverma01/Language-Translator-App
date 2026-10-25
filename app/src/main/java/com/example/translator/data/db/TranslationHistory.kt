package com.example.translator.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translation_history")
data class TranslationHistory(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val sourceText: String,

    val translatedText: String,

    val sourceLanguage: String,

    val targetLanguage: String,

    val timestamp: Long
)