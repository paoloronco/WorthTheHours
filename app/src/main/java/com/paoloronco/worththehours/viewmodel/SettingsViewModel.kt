package com.paoloronco.worththehours.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paoloronco.worththehours.data.local.UserPreferencesRepository
import com.paoloronco.worththehours.data.repository.SalaryRepository
import com.paoloronco.worththehours.ui.settings.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SalaryState(
    val hourlyWageInput: String = "",
    val monthlySalaryInput: String = "",
    val hoursPerMonthInput: String = "",
    val effectiveHourlyWage: Double = 0.0,
    val hourlyWageError: String? = null,
    val monthlySalaryError: String? = null,
    val hoursPerMonthError: String? = null,
    val salarySetSuccessfully: Boolean = false
)

sealed class SalaryEvent {
    data class OnHourlyWageChanged(val value: String) : SalaryEvent()
    data class OnMonthlySalaryChanged(val value: String) : SalaryEvent()
    data class OnHoursPerMonthChanged(val value: String) : SalaryEvent()
    object OnSaveHourlyWage : SalaryEvent()
    object OnSaveMonthlySalary : SalaryEvent()
    object OnSalarySetHandled : SalaryEvent()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val salaryRepository: SalaryRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode.asStateFlow()

    private val _uiState = MutableStateFlow(SalaryState())
    val uiState: StateFlow<SalaryState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingsDataStore.isDarkMode(),
                salaryRepository.hourlyWage
            ) { isDarkMode, wage ->
                _isDarkMode.value = isDarkMode
                _uiState.update { it.copy(effectiveHourlyWage = wage) }
            }.collect {}
        }
    }

    fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setDarkMode(isDarkMode)
        }
    }

    fun onEvent(event: SalaryEvent) {
        when (event) {
            is SalaryEvent.OnHourlyWageChanged -> {
                _uiState.update {
                    it.copy(
                        hourlyWageInput = event.value,
                        monthlySalaryInput = "",
                        hoursPerMonthInput = "",
                        hourlyWageError = null
                    )
                }
            }
            is SalaryEvent.OnMonthlySalaryChanged -> {
                _uiState.update {
                    it.copy(
                        monthlySalaryInput = event.value,
                        hourlyWageInput = "",
                        monthlySalaryError = null
                    )
                }
            }
            is SalaryEvent.OnHoursPerMonthChanged -> {
                _uiState.update {
                    it.copy(
                        hoursPerMonthInput = event.value,
                        hourlyWageInput = "",
                        hoursPerMonthError = null
                    )
                }
            }
            SalaryEvent.OnSaveHourlyWage -> saveHourlyWage()
            SalaryEvent.OnSaveMonthlySalary -> saveMonthlySalary()
            SalaryEvent.OnSalarySetHandled -> _uiState.update { it.copy(salarySetSuccessfully = false) }
        }
    }

    private fun saveHourlyWage() {
        val wageInput = _uiState.value.hourlyWageInput
        val wage = wageInput.toDoubleOrNull()
        if (wage == null || wage <= 0) {
            _uiState.update { it.copy(hourlyWageError = "Please enter a valid number") }
            return
        }

        viewModelScope.launch {
            salaryRepository.saveHourlyWage(wage)
            userPreferencesRepository.setIsSetupComplete(true)
            _uiState.update { it.copy(salarySetSuccessfully = true) }
        }
    }

    private fun saveMonthlySalary() {
        val salaryInput = _uiState.value.monthlySalaryInput
        val hoursInput = _uiState.value.hoursPerMonthInput

        val salary = salaryInput.toDoubleOrNull()
        val hours = hoursInput.toIntOrNull()

        var hasError = false
        if (salary == null || salary <= 0) {
            _uiState.update { it.copy(monthlySalaryError = "Please enter a valid number") }
            hasError = true
        }
        if (hours == null || hours <= 0) {
            _uiState.update { it.copy(hoursPerMonthError = "Please enter a valid number") }
            hasError = true
        }

        if (hasError || salary == null || hours == null) return

        val calculatedWage = salary / hours
        viewModelScope.launch {
            salaryRepository.saveHourlyWage(calculatedWage)
            userPreferencesRepository.setIsSetupComplete(true)
            _uiState.update { it.copy(salarySetSuccessfully = true) }
        }
    }
}