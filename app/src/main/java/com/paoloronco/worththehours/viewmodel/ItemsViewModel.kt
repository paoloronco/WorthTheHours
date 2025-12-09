package com.paoloronco.worththehours.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paoloronco.worththehours.data.repository.ItemRepository
import com.paoloronco.worththehours.data.repository.SalaryRepository
import com.paoloronco.worththehours.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemWorkTime(
    val item: Item,
    val workTime: String
)

data class ItemsState(
    val items: List<ItemWorkTime> = emptyList(),
    val itemNameInput: String = "",
    val itemPriceInput: String = "",
    val itemNameError: String? = null,
    val itemPriceError: String? = null,
    val itemAddedSuccessfully: Boolean = false,
    val isLoading: Boolean = true
)

sealed class ItemsEvent {
    data class OnItemNameChanged(val value: String) : ItemsEvent()
    data class OnItemPriceChanged(val value: String) : ItemsEvent()
    object SaveItem : ItemsEvent()
    data class DeleteItem(val itemWorkTime: ItemWorkTime) : ItemsEvent()
    object OnItemAddedHandled : ItemsEvent()
}

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val salaryRepository: SalaryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemsState())
    val uiState: StateFlow<ItemsState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            itemRepository.getItems().collect { items ->
                val hourlyWage = salaryRepository.hourlyWage.first()
                val itemsWithWorkTime = items.map { item ->
                    ItemWorkTime(
                        item = item,
                        workTime = formatWorkTime(item.price, hourlyWage)
                    )
                }
                _uiState.update { it.copy(items = itemsWithWorkTime, isLoading = false) }
            }
        }
    }

    fun onEvent(event: ItemsEvent) {
        when (event) {
            is ItemsEvent.OnItemNameChanged -> {
                _uiState.update { it.copy(itemNameInput = event.value, itemNameError = null) }
            }
            is ItemsEvent.OnItemPriceChanged -> {
                _uiState.update { it.copy(itemPriceInput = event.value, itemPriceError = null) }
            }
            is ItemsEvent.SaveItem -> saveItem()
            is ItemsEvent.DeleteItem -> {
                viewModelScope.launch {
                    itemRepository.deleteItem(event.itemWorkTime.item)
                }
            }
            is ItemsEvent.OnItemAddedHandled -> {
                _uiState.update {
                    it.copy(
                        itemAddedSuccessfully = false,
                        itemNameInput = "",
                        itemPriceInput = ""
                    )
                }
            }
        }
    }

    private fun saveItem() {
        val name = _uiState.value.itemNameInput
        val priceString = _uiState.value.itemPriceInput
        val price = priceString.toDoubleOrNull()

        var hasError = false
        if (name.isBlank()) {
            _uiState.update { it.copy(itemNameError = "Please enter a name") }
            hasError = true
        }
        if (price == null || price <= 0) {
            _uiState.update { it.copy(itemPriceError = "Please enter a valid price") }
            hasError = true
        }

        if (hasError || price == null) return

        viewModelScope.launch {
            val item = Item(name = name, price = price)
            itemRepository.insertItem(item)
            _uiState.update { it.copy(itemAddedSuccessfully = true) }
        }
    }

    private fun formatWorkTime(price: Double, hourlyWage: Double): String {
        if (hourlyWage <= 0) return "0h 0m"
        val hoursOfWork = price / hourlyWage
        val hours = hoursOfWork.toInt()
        val minutes = ((hoursOfWork - hours) * 60).toInt()
        return "${hours}h ${minutes}m"
    }
}
