package com.example.translator.utils

import com.example.translator.model.LanguageItem
import com.google.mlkit.nl.translate.TranslateLanguage

object LanguageConstants {

    val regionalLanguages = listOf(

        LanguageItem(
            TranslateLanguage.ENGLISH,
            "English"
        ),

        LanguageItem(
            TranslateLanguage.HINDI,
            "Hindi"
        ),

        LanguageItem(
            TranslateLanguage.BENGALI,
            "Bengali"
        ),

        LanguageItem(
            TranslateLanguage.MARATHI,
            "Marathi"
        ),

        LanguageItem(
            TranslateLanguage.TAMIL,
            "Tamil"
        ),

        LanguageItem(
            TranslateLanguage.TELUGU,
            "Telugu"
        ),

        LanguageItem(
            TranslateLanguage.URDU,
            "Urdu"
        ),

        LanguageItem(
            TranslateLanguage.GUJARATI,
            "Gujarati"
        ),

        LanguageItem(
            TranslateLanguage.KANNADA,
            "Kannada"
        )

    )

    val foreignLanguages = listOf(

        LanguageItem("en", "English"),
        LanguageItem("fr", "French"),
        LanguageItem("de", "German"),
        LanguageItem("es", "Spanish"),
        LanguageItem("it", "Italian"),
        LanguageItem("pt", "Portuguese"),
        LanguageItem("ru", "Russian"),
        LanguageItem("zh", "Chinese"),
        LanguageItem("ja", "Japanese"),
        LanguageItem("ko", "Korean"),
        LanguageItem("ar", "Arabic"),
        LanguageItem("hi","Hindi")
    )

}