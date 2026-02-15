package com.example.weatherweek5.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherweek5.data.model.WeatherResponse
import com.example.weatherweek5.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WeatherUiState(
    val city: String = "",
    val isLoading: Boolean = false,
    val result: WeatherResponse? = null,
    val error: String? = null
)

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun setCity(newCity: String) {
        _uiState.value = _uiState.value.copy(city = newCity)
    }

    fun fetchWeather() {
        val city = _uiState.value.city.trim()
        if (city.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Syötä kaupunki")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, result = null)
            try {
                val res = RetrofitInstance.api.getWeather(city)
                _uiState.value = _uiState.value.copy(isLoading = false, result = res)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Haku epäonnistui (tarkista kaupunki / api-key)"
                )
            }
        }
    }
}
