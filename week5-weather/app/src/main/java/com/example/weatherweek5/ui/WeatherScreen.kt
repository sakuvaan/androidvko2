package com.example.weatherweek5.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { vm.fetchWeather() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.loading
        ) {
            Text(if (state.loading) "Haetaan..." else "Hae sää")
        }

        Spacer(Modifier.height(16.dp))

        state.error?.let {
            Text(it)
            Spacer(Modifier.height(12.dp))
        }

        val city = state.shownCity
        val temp = state.tempC
        val desc = state.description

        if (city != null && temp != null && desc != null) {
            Text("Kaupunki: $city")
            Text("Lämpö: $temp °C")
            Text("Kuvaus: $desc")
        } else {
            Text("Ei vielä haettua dataa")
        }
    }
}