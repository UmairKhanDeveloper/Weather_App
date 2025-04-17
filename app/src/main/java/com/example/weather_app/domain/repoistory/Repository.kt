package com.example.weather_app.domain.repoistory

import com.example.weather_app.data.remote.api.Forecast
import com.example.weather_app.data.remote.api.Location
import com.example.weather_app.data.remote.api.weaterapi
import com.example.weather_app.data.remote.apiclient.ApiClientWeather
import com.example.weather_app.data.remote.apiclient.WeatherClient

class Repository : WeatherClient {
    override suspend fun getWeatherReport(location: String): weaterapi {
        return ApiClientWeather.getWeatherForecast(location)
    }
}
