package com.example.weatherweek5.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherweek5.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(vm: WeatherViewModel = viewModel()) {
    val state = vm.uiState.collectAsState().value

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Weather", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.city,
            onValueChange = { vm.setCity(it) },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { vm.fetchWeather() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hae sää")
        }

        Spacer(Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it)
        }

        state.result?.let { res ->
            Spacer(Modifier.height(8.dp))
            Text("Kaupunki: ${res.name}")
            Text("Lämpö: ${res.main.temp} °C")
            Text("Kuvaus: ${res.weather.firstOrNull()?.description ?: "-"}")
        }
    }
}
