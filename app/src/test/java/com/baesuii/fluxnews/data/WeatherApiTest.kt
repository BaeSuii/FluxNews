package com.baesuii.fluxnews.data

import com.baesuii.fluxnews.data.remote.api.WeatherApi
import com.baesuii.fluxnews.domain.model.Main
import com.baesuii.fluxnews.domain.model.Weather
import com.baesuii.fluxnews.domain.model.WeatherData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class WeatherApiTest : WeatherApi {
    private val weatherData = mutableListOf<WeatherData>()
    private var shouldThrowException = false

    private fun addWeatherData(data: WeatherData) {
        this.weatherData.add(data)
    }

    override suspend fun getWeather(city: String, apiKey: String): WeatherData {
        if (shouldThrowException) throw Exception("Fake exception")

        return weatherData.find { it.name == city }
            ?: throw Exception("City not found")
    }

    @Before
    fun setup() {
        addWeatherData(
            WeatherData(
                name = "New York",
                main = Main(temp = 25.0),
                weather = listOf(Weather(icon = "01d")),
                timezone = 3600
            )
        )

        addWeatherData(
            WeatherData(
                name = "Los Angeles",
                main = Main(temp = 30.0),
                weather = listOf(Weather(icon = "02d")),
                timezone = 3600
            )
        )
    }

    @Test
    fun getWeather_returnsCorrectData_forValidCity() = runBlocking {
        // Act
        val result = getWeather(city = "New York", apiKey = "fakeApiKey")

        // Assert
        assertEquals("New York", result.name)
        assertEquals(25.0, result.main.temp, 0.0)
        assertEquals("01d", result.weather.first().icon)
    }

    @Test
    fun getWeather_throwsException_forInvalidCity(): Unit = runBlocking {

        try {
            getWeather(city = "UnknownCity", apiKey = "fakeApiKey")
        } catch (e: Exception) {
            assertEquals("City not found", e.message)
        }
    }

    @Test
    fun getWeather_throwsException_whenExceptionFlagIsSet(): Unit = runBlocking {
        // Arrange
        shouldThrowException = true

        try {
            getWeather(city = "New York", apiKey = "fakeApiKey")
        } catch (e: Exception) {
            assertEquals("Fake exception", e.message)
        }
    }
}