package com.example.weather_app.data.remote.apiclient

import com.example.weather_app.data.remote.api.Forecast
import com.example.weather_app.data.remote.api.weaterapi


interface WeatherClient {
    suspend fun getWeatherReport(location: String): weaterapi
}
