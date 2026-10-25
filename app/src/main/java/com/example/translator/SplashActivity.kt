package com.example.translator

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.translator.ui.theme.TranslatorTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {

            setContent {

                TranslatorTheme {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Image(
                            painter = painterResource(R.drawable.translate_app_icon),
                            contentDescription = "Splash Screen Logo",
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }

            delay(2000)

            startActivity(
                Intent(
                    this@SplashActivity,
                    MainActivity::class.java
                )
            )

            finish()
        }
    }
}