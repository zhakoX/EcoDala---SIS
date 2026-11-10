package com.ecodala.presentation.weather

import com.ecodala.domain.model.WeatherDay
import com.ecodala.domain.repository.WeatherRepository
import com.ecodala.core.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {
    @get:Rule val dispatcherRule = MainDispatcherRule()

    @Test fun successThenOfflineRetryThenRecovery() = runTest {
        var offline = false
        val days = listOf(WeatherDay("2026-11-09", 1.0, 12.0))
        val repository = object : WeatherRepository {
            override suspend fun getItems(): List<WeatherDay> {
                if (offline) throw IOException("Offline")
                return days
            }
        }
        val model = WeatherViewModel(repository)
        assertEquals(WeatherUiState.Loading, model.state.value)
        runCurrent()
        assertEquals(WeatherUiState.Success(days), model.state.value)

        offline = true
        model.retry()
        assertEquals(WeatherUiState.Loading, model.state.value)
        runCurrent()
        assertTrue(model.state.value is WeatherUiState.Error)

        offline = false
        model.retry()
        runCurrent()
        assertEquals(WeatherUiState.Success(days), model.state.value)
    }
}
