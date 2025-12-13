package com.paoloronco.worththehours.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.paoloronco.worththehours.viewmodel.SalaryViewModel

@Composable
fun SalaryScreen(
    salaryViewModel: SalaryViewModel = hiltViewModel(),
    onSalarySaved: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Salary Screen")
        Button(onClick = {
            salaryViewModel.saveSalary()
            onSalarySaved()
        }) {
            Text("Save")
        }
    }
}
