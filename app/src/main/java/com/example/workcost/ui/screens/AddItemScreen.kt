package com.example.workcost.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workcost.viewmodel.ItemsEvent
import com.example.workcost.viewmodel.ItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    onNavigateBack: () -> Unit,
    viewModel: ItemsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.itemAddedSuccessfully) {
        if (state.itemAddedSuccessfully) {
            onNavigateBack()
            viewModel.onEvent(ItemsEvent.OnItemAddedHandled) // Reset the flag
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Item", style = MaterialTheme.typography.headlineSmall) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
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
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val itemNameError = state.itemNameError
                    OutlinedTextField(
                        value = state.itemNameInput,
                        onValueChange = { viewModel.onEvent(ItemsEvent.OnItemNameChanged(it)) },
                        label = { Text("Item Name") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = itemNameError != null,
                        leadingIcon = { Icon(Icons.Default.ShoppingBag, contentDescription = null) },
                        singleLine = true
                    )
                    if (itemNameError != null) {
                        Text(
                            text = itemNameError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    val itemPriceError = state.itemPriceError
                    OutlinedTextField(
                        value = state.itemPriceInput,
                        onValueChange = { viewModel.onEvent(ItemsEvent.OnItemPriceChanged(it)) },
                        label = { Text("Price") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = itemPriceError != null,
                        leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                        singleLine = true
                    )
                    if (itemPriceError != null) {
                        Text(
                            text = itemPriceError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onEvent(ItemsEvent.SaveItem) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = state.itemNameInput.isNotBlank() && state.itemPriceInput.isNotBlank()
            ) {
                Text("Save Item", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
