package com.example.translator.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertHistory(
        history: TranslationHistory
    )

    @Query(
        "SELECT * FROM translation_history ORDER BY timestamp DESC"
    )
    fun getAllHistory():
            Flow<List<TranslationHistory>>

    @Query(
        "DELETE FROM translation_history"
    )
    suspend fun clearHistory()
}