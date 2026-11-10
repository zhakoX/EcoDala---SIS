package com.ecodala.domain.repository

import com.ecodala.domain.model.WeatherDay

interface WeatherRepository {
    suspend fun getItems(): List<WeatherDay>
}
