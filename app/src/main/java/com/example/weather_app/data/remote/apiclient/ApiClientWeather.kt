package com.example.weather_app.data.remote.apiclient

import com.example.weather_app.data.remote.api.Forecast
import com.example.weather_app.data.remote.api.weaterapi
import com.example.weather_app.data.remote.constant.Constant
import com.example.weather_app.data.remote.constant.Constant.TIMEOUT
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object ApiClientWeather {

    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    prettyPrint = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }

        install(HttpTimeout) {
            socketTimeoutMillis = Constant.TIMEOUT
            requestTimeoutMillis = Constant.TIMEOUT
            connectTimeoutMillis = Constant.TIMEOUT
        }
    }

    suspend fun getWeatherForecast(location: String): weaterapi {
        val formattedLocation = location.replace(" ", "+")
        val url = "${Constant.BASE_URL}/forecast.json?key=${Constant.KEY}&q=$formattedLocation&days=3"

        val response: HttpResponse = client.get(url) {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }

        return response.body()
    }
}

