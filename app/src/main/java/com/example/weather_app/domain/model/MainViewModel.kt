package com.example.weather_app.domain.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.remote.api.Forecast
import com.example.weather_app.data.remote.api.weaterapi
import com.example.weather_app.domain.repoistory.Repository
import com.example.weather_app.domain.usecase.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _allWeatherReport = MutableStateFlow<ResultState<weaterapi>>(ResultState.Loading)
    val allWeatherReport: StateFlow<ResultState<weaterapi>> = _allWeatherReport.asStateFlow()

    fun getWeather(location: String) {
        viewModelScope.launch {
            _allWeatherReport.value = ResultState.Loading
            try {
                val response = repository.getWeatherReport(location)
                _allWeatherReport.value = ResultState.Succses(response)
            } catch (e: Exception) {
                _allWeatherReport.value = ResultState.Error(e)
            }
        }
    }
}
