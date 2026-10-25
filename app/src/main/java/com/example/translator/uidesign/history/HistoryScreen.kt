package com.example.translator.uidesign.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translator.data.db.AppDatabase
import com.example.translator.data.repository.HistoryRepository
import com.example.translator.viewmodel.HistoryViewModel
import com.example.translator.viewmodel.HistoryViewModelFactory

@Composable
fun HistoryScreen() {

    val context = LocalContext.current

    val historyViewModel: HistoryViewModel =
        viewModel(
            factory = HistoryViewModelFactory(
                HistoryRepository(
                    AppDatabase
                        .getDatabase(context)
                        .historyDao()
                )
            )
        )

    val historyList by
    historyViewModel
        .historyList
        .collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                historyViewModel.clearHistory()
            }
        ) {
            Text("Clear History")
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            items(historyList) { item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                    ) {

                        Text(
                            text = item.sourceText,
                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium
                        )

                        Text(
                            text = item.translatedText
                        )

                        Text(
                            text =
                                "${item.sourceLanguage} → ${item.targetLanguage}"
                        )
                    }
                }
            }
        }
    }
}