package com.ecodala.data.repository

import com.ecodala.data.remote.DailyWeatherDto
import com.ecodala.data.remote.WeatherApi
import com.ecodala.data.remote.WeatherResponseDto
import com.ecodala.domain.model.WeatherDay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class WeatherRepositoryImplTest {
    private fun repository(daily: DailyWeatherDto?) = WeatherRepositoryImpl(object : WeatherApi {
        override suspend fun getForecast() = WeatherResponseDto(daily)
    })

    @Test fun mapsDtoToDomain() = runTest {
        val actual = repository(DailyWeatherDto(listOf("2026-11-09"), listOf(1.0), listOf(12.0))).getItems()
        assertEquals(listOf(WeatherDay("2026-11-09", 1.0, 12.0)), actual)
    }

    @Test(expected = IOException::class)
    fun malformedResponseFailsInsteadOfTruncating() = runTest {
        repository(DailyWeatherDto(listOf("2026-11-09"), emptyList(), listOf(12.0))).getItems()
    }
}
