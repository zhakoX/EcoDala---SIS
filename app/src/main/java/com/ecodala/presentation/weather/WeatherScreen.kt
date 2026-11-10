package com.ecodala.presentation.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ecodala.data.remote.WeatherApiClient
import com.ecodala.data.repository.WeatherRepositoryImpl

@Composable
fun WeatherRoute(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    val viewModel: WeatherViewModel = viewModel(factory = viewModelFactory {
        initializer { WeatherViewModel(WeatherRepositoryImpl(WeatherApiClient.api)) }
    })
    val state by viewModel.state.collectAsStateWithLifecycle()
    WeatherScreen(state, onBackClick, viewModel::retry, modifier)
}

@Composable
fun WeatherScreen(
    state: WeatherUiState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.statusBarsPadding().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(onClick = onBackClick) { Text("Back") }
                // Retry is available after success too, to test offline requests.
                TextButton(onClick = onRetry, enabled = state !is WeatherUiState.Loading) {
                    Text("Retry")
                }
            }
            Text("Kyzylorda · 7-day forecast", style = MaterialTheme.typography.titleLarge)
            Text("Weather data: Open-Meteo.com", style = MaterialTheme.typography.bodySmall)
            when (state) {
                WeatherUiState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) { CircularProgressIndicator() }
                is WeatherUiState.Error -> Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                    Button(onClick = onRetry) { Text("Retry") }
                }
                is WeatherUiState.Success -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.items, key = { it.date }) { day ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(day.date, style = MaterialTheme.typography.titleMedium)
                                Text("Low: ${day.minimumCelsius} °C · High: ${day.maximumCelsius} °C")
                            }
                        }
                    }
                }
            }
        }
    }
}
