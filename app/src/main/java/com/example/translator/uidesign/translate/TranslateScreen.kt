package com.example.translator.uidesign.translate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox


import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translator.utils.LanguageConstants
import com.example.translator.viewmodel.TranslatorViewModel

import androidx.compose.ui.platform.LocalContext
import com.example.translator.data.db.AppDatabase
import com.example.translator.data.repository.HistoryRepository
import com.example.translator.viewmodel.HistoryViewModel
import com.example.translator.viewmodel.HistoryViewModelFactory

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.translator.utils.TTSManager

import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

import android.Manifest
import android.net.Uri
import java.io.File

import androidx.activity.compose.rememberLauncherForActivityResult

import androidx.compose.material.icons.filled.CameraAlt

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.core.content.FileProvider
import com.example.translator.model.TranslationType

import com.example.translator.utils.TextRecognitionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslateScreen(
    translationType: TranslationType
) {

    val context = LocalContext.current

    val translatorViewModel: TranslatorViewModel =
        viewModel()

    val historyViewModel: HistoryViewModel =
        viewModel(
            factory =
                HistoryViewModelFactory(
                    HistoryRepository(
                        AppDatabase
                            .getDatabase(context)
                            .historyDao()
                    )
                )
        )

    val translatedText by
    translatorViewModel
        .translatedText
        .collectAsState()

    var inputText by rememberSaveable {
        mutableStateOf("")
    }

    var sourceLanguageName by rememberSaveable {
        mutableStateOf("English")
    }

    var targetLanguageName by rememberSaveable {
        mutableStateOf("Hindi")
    }

    var sourceLanguageCode by rememberSaveable {
        mutableStateOf("en")
    }

    var targetLanguageCode by rememberSaveable {
        mutableStateOf("hi")
    }

    var sourceExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    var targetExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val clipboardManager =
        LocalClipboardManager.current

    val ttsManager =
        remember {
            TTSManager(context)
        }

    val speechLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (
                result.resultCode ==
                Activity.RESULT_OK
            ) {

                val speechText =
                    result.data
                        ?.getStringArrayListExtra(
                            RecognizerIntent.EXTRA_RESULTS
                        )

                if (
                    !speechText.isNullOrEmpty()
                ) {

                    inputText =
                        speechText[0]
                }
            }
        }

    // camera

    var showImagePickerDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var imageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    val textRecognitionManager =
        remember {
            TextRecognitionManager()
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->

            uri?.let {

                textRecognitionManager.recognizeText(
                    context,
                    it
                ) { extractedText ->

                    inputText = extractedText
                }
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->

            if (
                success &&
                imageUri != null
            ) {

                textRecognitionManager.recognizeText(
                    context,
                    imageUri!!
                ) { extractedText ->

                    inputText = extractedText
                }
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {

                cameraLauncher.launch(
                    imageUri!!
                )
            }
        }

    // translate screen

    val languageList =

        remember(
            translationType
        ) {

            when (
                translationType
            ) {

                TranslationType.REGIONAL ->
                    LanguageConstants.regionalLanguages

                TranslationType.FOREIGN ->
                    LanguageConstants.foreignLanguages
            }
        }



    fun startVoiceInput() {

        val intent =
            Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
            )

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        speechLauncher.launch(intent)
    }

    fun shareTranslation() {

        val intent =
            Intent(
                Intent.ACTION_SEND
            )

        intent.type =
            "text/plain"

        intent.putExtra(
            Intent.EXTRA_TEXT,
            translatedText
        )

        context.startActivity(
            Intent.createChooser(
                intent,
                "Share Translation"
            )
        )
    }

    LaunchedEffect(translatedText) {

        if (
            translatedText.isNotBlank() &&
            inputText.isNotBlank()
        ) {

            historyViewModel.saveHistory(
                sourceText = inputText,
                translatedText = translatedText,
                sourceLanguage = sourceLanguageName,
                targetLanguage = targetLanguageName
            )
        }
    }

    if (showImagePickerDialog) {

        AlertDialog(

            onDismissRequest = {

                showImagePickerDialog = false
            },

            title = {

                Text(
                    "Choose Image Source"
                )
            },

            text = {

                Column {

                    TextButton(

                        onClick = {

                            showImagePickerDialog =
                                false

                            val imageFile =
                                File(
                                    context.cacheDir,
                                    "camera_image.jpg"
                                )

                            imageUri =
                                FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.provider",
                                    imageFile
                                )

                            cameraPermissionLauncher.launch(
                                Manifest.permission.CAMERA
                            )
                        }
                    ) {

                        Text(
                            "📷 Camera"
                        )
                    }

                    TextButton(

                        onClick = {

                            showImagePickerDialog =
                                false

                            galleryLauncher.launch(
                                "image/*"
                            )
                        }
                    ) {

                        Text(
                            "🖼 Gallery"
                        )
                    }
                }
            },

            confirmButton = {}
        )
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
    ) {

        Text(
            text = "Translator",
            style =
                MaterialTheme
                    .typography
                    .headlineMedium
        )

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        OutlinedTextField(

            value = inputText,

            onValueChange = {

                inputText = it
            },

            modifier =
                Modifier.fillMaxWidth(),

            label = {

                Text("Enter Text")
            },

            trailingIcon = {

                IconButton(

                    onClick = {

                        showImagePickerDialog = true
                    }
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.CameraAlt,

                        contentDescription =
                            "Scan Text"
                    )
                }
            }
        )

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        // Source Language

        ExposedDropdownMenuBox(
            expanded = sourceExpanded,
            onExpandedChange = {
                sourceExpanded =
                    !sourceExpanded
            }
        ) {

            OutlinedTextField(
                value = sourceLanguageName,
                onValueChange = {},
                readOnly = true,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                label = {
                    Text(
                        "Source Language"
                    )
                }
            )

            ExposedDropdownMenu(
                expanded =
                    sourceExpanded,
                onDismissRequest = {
                    sourceExpanded =
                        false
                }
            ) {

                languageList
                    .forEach {

                        DropdownMenuItem(
                            text = {
                                Text(it.name)
                            },
                            onClick = {

                                sourceLanguageName =
                                    it.name

                                sourceLanguageCode =
                                    it.code

                                sourceExpanded =
                                    false
                            }
                        )
                    }
            }
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        // Target Language

        ExposedDropdownMenuBox(
            expanded = targetExpanded,
            onExpandedChange = {
                targetExpanded =
                    !targetExpanded
            }
        ) {

            OutlinedTextField(
                value = targetLanguageName,
                onValueChange = {},
                readOnly = true,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                label = {
                    Text(
                        "Target Language"
                    )
                }
            )

            ExposedDropdownMenu(
                expanded =
                    targetExpanded,
                onDismissRequest = {
                    targetExpanded =
                        false
                }
            ) {

                languageList
                    .forEach {

                        DropdownMenuItem(
                            text = {
                                Text(it.name)
                            },
                            onClick = {

                                targetLanguageName =
                                    it.name

                                targetLanguageCode =
                                    it.code

                                targetExpanded =
                                    false
                            }
                        )
                    }
            }
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceEvenly
        ) {

            IconButton(
                onClick = {
                    startVoiceInput()
                }
            ) {

                Icon(
                    Icons.Default.Mic,
                    contentDescription =
                        "Mic"
                )
            }

            IconButton(
                onClick = {

                    ttsManager.speak(
                        translatedText
                    )
                }
            ) {

                Icon(
                    Icons.Default.VolumeUp,
                    contentDescription =
                        "Speak"
                )
            }

            IconButton(
                onClick = {

                    clipboardManager.setText(
                        AnnotatedString(
                            translatedText
                        )
                    )
                }
            ) {

                Icon(
                    Icons.Default.ContentCopy,
                    contentDescription =
                        "Copy"
                )
            }

            IconButton(
                onClick = {
                    shareTranslation()
                }
            ) {

                Icon(
                    Icons.Default.Share,
                    contentDescription =
                        "Share"
                )
            }

            IconButton(
                onClick = {

                    val tempCode =
                        sourceLanguageCode

                    sourceLanguageCode =
                        targetLanguageCode

                    targetLanguageCode =
                        tempCode

                    val tempName =
                        sourceLanguageName

                    sourceLanguageName =
                        targetLanguageName

                    targetLanguageName =
                        tempName
                }
            ) {

                Icon(
                    Icons.Default.SwapHoriz,
                    contentDescription =
                        "Swap"
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        Button(
            modifier =
                Modifier.fillMaxWidth(),
            onClick = {

                translatorViewModel.translate(
                    text = inputText,
                    sourceLanguage =
                        sourceLanguageCode,
                    targetLanguage =
                        targetLanguageCode
                )
            }
        ) {

            Text(
                "Translate"
            )
        }

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        Card(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Column(
                modifier =
                    Modifier.padding(16.dp)
            ) {

                Text(
                    text =
                        "Translated Text"
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                Text(
                    text =
                        translatedText
                )
            }
        }
    }
}