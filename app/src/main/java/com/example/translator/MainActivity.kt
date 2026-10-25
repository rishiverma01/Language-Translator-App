package com.example.translator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.translator.navigation.AppNavigation
import com.example.translator.ui.theme.TranslatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            TranslatorTheme {

                AppNavigation()
            }
        }
    }
}