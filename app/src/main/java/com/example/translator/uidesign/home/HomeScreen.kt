package com.example.translator.uidesign.home

import android.view.RoundedCorner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.translator.R
import com.example.translator.ui.theme.Background
import com.example.translator.ui.theme.Secondary
import com.example.translator.ui.theme.white

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview(
showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController()
    )
}


@Composable
fun HomeScreen(
    navController: NavController
) {

    var selectedCard by rememberSaveable {
        mutableStateOf(0)
    }

    // main container

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Background)
    ) {
        // top bar horizontally

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(white)
        ) {
            Image(
                painter = painterResource(R.drawable.home_small_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(30.dp)
            )

            Spacer(
                Modifier.width(10.dp)
            )

            Text(
                text = "Translator",
                fontSize = 16.sp,
                color = Secondary,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                Modifier.weight(1f)
            )

            IconButton(

                    onClick = {

                        navController.navigate(
                            "settings"
                        )
                    }

            ) {
                Image(
                    painter = painterResource(R.drawable.home_setting_icon_svg),
                    contentDescription = "Settings Icon",
                    modifier = Modifier
                        .size(40.dp)
                )
            }

        }

        // center logo

        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 100.dp
            ),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .padding(top = 80.dp)
                .align (Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(R.drawable.translate_app_icon),
                contentDescription = null,
                modifier = Modifier.size(140.dp)
            )
        }

        // welcome text

        Text(
            text = "Welcome To The App",
            modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 12.sp,
            fontWeight = FontWeight(300),
            color = Color(0xFF454652),
            letterSpacing = 1.sp
        )

        Text(
            text = "Matrabhasa",
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 22.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF24389C)
        )

        Spacer(modifier = Modifier.height(22.dp))

        LanguageCard(
            title = "Regional Language",
            description = "Support for local and native tongue",
            isSelected = selectedCard == 0,
            onClick = {
                selectedCard = 0
                navController.navigate("translate/regional") {

                    launchSingleTop = true

                    restoreState = true
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        LanguageCard(
            title = "Foreign Languages",
            description = "Global communication made easy",
            isSelected = selectedCard == 1,
            onClick = {
                selectedCard = 1
                navController.navigate("translate/foreign") {

                    launchSingleTop = true

                    restoreState = true
                }
            }
        )

    }
}

@Composable
fun LanguageCard(
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF4E5FEA),
            Color(0xFFD000FF)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() }
            .background(
                brush = gradient,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(2.dp) // border thickness
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = if (isSelected) {
                        gradient
                    } else {
                        Brush.horizontalGradient(
                            listOf(Color.White, Color.White)
                        )
                    },
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "🌐",
                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = title,
                        color = if (isSelected) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = description,
                        color = if (isSelected)
                            Color.White.copy(alpha = 0.9f)
                        else
                            Color.Gray
                    )
                }

                Text(
                    text = "→",
                    fontSize = 24.sp,
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }
    }
}








//    Box(
//
//        modifier = Modifier
//            .fillMaxSize(),
//
//        contentAlignment =
//            Alignment.Center
//    ) {
//
//        Column(
//
//            horizontalAlignment =
//                Alignment.CenterHorizontally
//        ) {
//
//            Text(
//
//                text =
//                    "Welcome To Transloator",
//
//                style =
//                    MaterialTheme
//                        .typography
//                        .headlineMedium
//            )
//
//            Spacer(
//                modifier =
//                    Modifier.height(
//                        16.dp
//                    )
//            )
//
//            Text(
//                text =
//                    "22 Indian Languages Supported"
//            )
//        }
//    }
