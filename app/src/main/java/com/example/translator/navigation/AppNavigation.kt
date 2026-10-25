package com.example.translator.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.translator.model.BottomNavItem
import com.example.translator.model.TranslationType
import com.example.translator.uidesign.history.HistoryScreen
import com.example.translator.uidesign.home.HomeScreen
import com.example.translator.uidesign.settings.SettingsScreen
import com.example.translator.uidesign.translate.TranslateScreen

@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    val items = listOf(

        BottomNavItem(
            "home",
            "Home"
        ),

//        BottomNavItem(
//            "translate",
//            "Translate"
//        ),

//        BottomNavItem(
//            "history",
//            "History"
//        )
    )

    Scaffold(

        bottomBar = {

            NavigationBar {

                val currentRoute =
                    navController
                        .currentBackStackEntryAsState()
                        .value
                        ?.destination
                        ?.route

                items.forEach {

                    NavigationBarItem(

                        selected =
                            currentRoute == it.route,

                        onClick = {

                            navController.navigate(it.route) {

                                popUpTo(
                                    navController.graph.startDestinationId
                                ) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }
                        },

                        label = {
                            Text(it.title)
                        },

                        icon = { }
                    )
                }
            }
        }

    ) { padding ->

        NavHost(

            navController = navController,

            startDestination = "home",

            modifier =
                Modifier.padding(
                    padding
                )
        ) {

            composable("home") {

                HomeScreen(
                    navController = navController
                )
            }

            composable(
                route = "translate/{type}"
            ) {

                val typeString =
                    it.arguments?.getString("type")

                val translationType =

                    if (
                        typeString == "foreign"
                    ) {

                        TranslationType.FOREIGN

                    } else {

                        TranslationType.REGIONAL
                    }

                TranslateScreen(
                    translationType = translationType
                )
            }

            composable("settings") {

                SettingsScreen(
                    navController = navController
                )
            }

            composable("history") {

                HistoryScreen()
            }
        }
    }
}