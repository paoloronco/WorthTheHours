package com.paoloronco.worththehours.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paoloronco.worththehours.viewmodel.SalaryEvent
import com.paoloronco.worththehours.viewmodel.SalaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalaryScreen(
    onNavigateBack: () -> Unit,
    viewModel: SalaryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.salarySetSuccessfully) {
        if (state.salarySetSuccessfully) {
            onNavigateBack()
            viewModel.onEvent(SalaryEvent.OnSalarySetHandled)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Salary Settings") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            // Effective Hourly Wage Display
            if (state.effectiveHourlyWage > 0) {
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
                            text = "$%.2f".format(state.effectiveHourlyWage),
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
                    val hourlyWageError = state.hourlyWageError
                    OutlinedTextField(
                        value = state.hourlyWageInput,
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
                        enabled = state.hourlyWageInput.isNotBlank()
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
                    val monthlySalaryError = state.monthlySalaryError
                    OutlinedTextField(
                        value = state.monthlySalaryInput,
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
                    val hoursPerMonthError = state.hoursPerMonthError
                    OutlinedTextField(
                        value = state.hoursPerMonthInput,
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
                        enabled = state.monthlySalaryInput.isNotBlank() && state.hoursPerMonthInput.isNotBlank()
                    ) {
                        Text("Calculate and Save")
                    }
                }
            }
        }
    }
}
