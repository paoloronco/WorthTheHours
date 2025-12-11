package com.paoloronco.worththehours.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paoloronco.worththehours.ui.screens.AddItemScreen
import com.paoloronco.worththehours.ui.screens.ItemListScreen
import com.paoloronco.worththehours.ui.screens.SettingsScreen
import com.paoloronco.worththehours.viewmodel.MainViewModel

@Composable
fun NavGraph(mainViewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val startDestination by mainViewModel.startDestination.collectAsState()

    if (startDestination.isNotEmpty()) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable("itemList") {
                ItemListScreen(
                    onAddItem = { navController.navigate("addItem") },
                    onGoToSettings = { navController.navigate("settings") }
                )
            }
            composable("addItem") {
                AddItemScreen(onNavigateBack = { navController.popBackStack() })
            }
            composable("settings") {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
