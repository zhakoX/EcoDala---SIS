package com.ecodala.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

interface WeatherApi {
    // Fixed Kyzylorda coordinates: no location permission is required.
    @GET("v1/forecast?latitude=44.85&longitude=65.50&daily=temperature_2m_max,temperature_2m_min&timezone=Asia%2FQyzylorda&forecast_days=7")
    suspend fun getForecast(): WeatherResponseDto
}

data class WeatherResponseDto(val daily: DailyWeatherDto?)

data class DailyWeatherDto(
    val time: List<String>?,
    @SerializedName("temperature_2m_min") val minimum: List<Double?>?,
    @SerializedName("temperature_2m_max") val maximum: List<Double?>?,
)
