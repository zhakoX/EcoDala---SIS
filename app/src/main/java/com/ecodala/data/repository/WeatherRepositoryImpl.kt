package com.ecodala.data.repository

import com.ecodala.data.remote.WeatherApi
import com.ecodala.domain.model.WeatherDay
import com.ecodala.domain.repository.WeatherRepository
import java.io.IOException

class WeatherRepositoryImpl(private val api: WeatherApi) : WeatherRepository {
    override suspend fun getItems(): List<WeatherDay> {
        val daily = api.getForecast().daily ?: throw IOException("Missing daily forecast")
        val dates = daily.time ?: throw IOException("Missing forecast dates")
        val minimum = daily.minimum ?: throw IOException("Missing minimum temperatures")
        val maximum = daily.maximum ?: throw IOException("Missing maximum temperatures")
        if (dates.isEmpty() || dates.size != minimum.size || dates.size != maximum.size) {
            throw IOException("Invalid forecast response")
        }
        return dates.indices.map { index ->
            WeatherDay(
                date = dates[index],
                minimumCelsius = minimum[index] ?: throw IOException("Missing temperature"),
                maximumCelsius = maximum[index] ?: throw IOException("Missing temperature"),
            )
        }
    }
}
