
package com.example.workcost.data.repository

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SalaryRepository(private val sharedPreferences: SharedPreferences) {

    private val _hourlyWage = MutableStateFlow(0.0)
    val hourlyWage: Flow<Double> = _hourlyWage

    init {
        _hourlyWage.value = sharedPreferences.getFloat("hourly_wage", 0f).toDouble()
    }

    fun saveHourlyWage(wage: Double) {
        sharedPreferences.edit().putFloat("hourly_wage", wage.toFloat()).apply()
        _hourlyWage.value = wage
    }
}
