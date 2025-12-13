package com.paoloronco.worththehours.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paoloronco.worththehours.R
import com.paoloronco.worththehours.viewmodel.SalaryEvent
import com.paoloronco.worththehours.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: (() -> Unit)? = null,
    onSalarySaved: (() -> Unit)? = null,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val salaryState by viewModel.uiState.collectAsState()

    LaunchedEffect(salaryState.salarySetSuccessfully) {
        if (salaryState.salarySetSuccessfully) {
            onSalarySaved?.invoke()
            viewModel.onEvent(SalaryEvent.OnSalarySetHandled) // Reset the event
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.settings)) },
                navigationIcon = {
                    if (onNavigateBack != null) {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.dark_mode))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { viewModel.setDarkMode(it) }
                )
            }
            // Effective Hourly Wage Display
            if (salaryState.effectiveHourlyWage > 0) {
                Spacer(Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Effective Hourly Wage",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$%.2f".format(salaryState.effectiveHourlyWage),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
            }

            // Hourly Wage Section
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val hourlyWageError = salaryState.hourlyWageError
                    OutlinedTextField(
                        value = salaryState.hourlyWageInput,
                        onValueChange = { viewModel.onEvent(SalaryEvent.OnHourlyWageChanged(it)) },
                        label = { Text("Hourly Net Wage") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = hourlyWageError != null,
                        leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                        singleLine = true
                    )
                    if (hourlyWageError != null) {
                        Text(
                            text = hourlyWageError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.onEvent(SalaryEvent.OnSaveHourlyWage) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = salaryState.hourlyWageInput.isNotBlank()
                    ) {
                        Text("Save Hourly Wage")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                text = "OR",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(Modifier.height(16.dp))

            // Monthly Salary Section
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val monthlySalaryError = salaryState.monthlySalaryError
                    OutlinedTextField(
                        value = salaryState.monthlySalaryInput,
                        onValueChange = { viewModel.onEvent(SalaryEvent.OnMonthlySalaryChanged(it)) },
                        label = { Text("Monthly Net Salary") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = monthlySalaryError != null,
                        leadingIcon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
                        singleLine = true
                    )
                    if (monthlySalaryError != null) {
                        Text(
                            text = monthlySalaryError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    val hoursPerMonthError = salaryState.hoursPerMonthError
                    OutlinedTextField(
                        value = salaryState.hoursPerMonthInput,
                        onValueChange = { viewModel.onEvent(SalaryEvent.OnHoursPerMonthChanged(it)) },
                        label = { Text("Working Hours per Month") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = hoursPerMonthError != null,
                        leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                        singleLine = true
                    )
                    if (hoursPerMonthError != null) {
                        Text(
                            text = hoursPerMonthError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.onEvent(SalaryEvent.OnSaveMonthlySalary) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = salaryState.monthlySalaryInput.isNotBlank() && salaryState.hoursPerMonthInput.isNotBlank()
                    ) {
                        Text("Calculate and Save")
                    }
                }
            }
        }
    }
}