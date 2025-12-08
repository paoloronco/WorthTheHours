package com.example.workcost.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workcost.ui.screens.AddItemScreen
import com.example.workcost.ui.screens.ItemListScreen
import com.example.workcost.ui.screens.SalaryScreen
import com.example.workcost.viewmodel.MainViewModel

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
                    onGoToSettings = { navController.navigate("salary") }
                )
            }
            composable("addItem") {
                AddItemScreen(onNavigateBack = { navController.popBackStack() })
            }
            composable("salary") {
                SalaryScreen(
                    onNavigateBack = {
                        if (navController.previousBackStackEntry != null) {
                            navController.popBackStack()
                        } else {
                            navController.navigate("itemList") {
                                popUpTo("salary") { inclusive = true }
                            }
                        }
                    }
                )
            }
        }
    }
}
