package com.hamilton.weatherapp.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamilton.services.open_weather_map.api.WeatherRepository
import com.hamilton.weatherapp.landing.models.CurrentWeatherUiModelMapper
import com.hamilton.weatherapp.landing.models.HourlyWeatherUiModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LandingScreenState())
    val uiState: StateFlow<LandingScreenState> = _uiState.asStateFlow()

    fun getCurrentWeather(latitude: Double, longitude: Double, isRefreshing: Boolean = false) {
        if (isRefreshing) {
            _uiState.update { currentState ->
                currentState.copy(isRefreshing = true)
            }
        } else {
            showLoadingState()
        }
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val currentWeather = weatherRepository.getCurrentWeather(
                    lat = latitude,
                    long = longitude
                )

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isRefreshing = false,
                        latLong = LatLong(latitude = latitude, longitude = longitude),
                        currentWeatherUiModel = CurrentWeatherUiModelMapper.toCurrentWeatherUiModel(
                            weatherData = currentWeather
                        )
                    )
                }
            }
        } catch (e: Exception) {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    isRefreshing = false
                )
            }
        }
    }

    fun getForecastWeather(latitude: Double, longitude: Double) {
        showLoadingState()
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val forecastWeather = weatherRepository.getForecastWeather(
                    lat = latitude,
                    long = longitude
                )

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        hourlyWeatherUiModels = forecastWeather.map { weatherData ->
                            HourlyWeatherUiModelMapper.toHourlyWeatherUiModel(
                                weatherData = weatherData
                            )
                        }
                    )
                }
            }
        } catch (e: Exception) {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false
                )
            }
        }
    }

    fun updatePermissionState(isLocationPermissionGranted: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isLocationPermissionGranted = isLocationPermissionGranted)
        }
    }

    fun updateShouldShowPermissionRationale(shouldShow: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                shouldShowPermissionRationale = shouldShow
            )
        }
    }

    private fun showLoadingState() {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }
    }
}