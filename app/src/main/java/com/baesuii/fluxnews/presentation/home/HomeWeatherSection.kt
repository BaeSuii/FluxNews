package com.baesuii.fluxnews.presentation.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.data.remote.api.WeatherApi
import com.baesuii.fluxnews.domain.model.WeatherData
import com.baesuii.fluxnews.presentation.theme.Dimensions.articleCardSizeHeight
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconWeatherSize
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingNormal
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.presentation.theme.statusBarColorLight
import com.baesuii.fluxnews.presentation.theme.statusBarColorNight
import com.baesuii.fluxnews.util.Constants.WEATHER_KEY
import com.baesuii.fluxnews.util.Constants.WEATHER_URL
import com.baesuii.fluxnews.util.formatDay
import com.baesuii.fluxnews.util.weatherDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeWeatherBar(){
    var weatherData by remember { mutableStateOf<WeatherData?>(null) }

    val apiKey = WEATHER_KEY
    val weatherApi: WeatherApi = Retrofit.Builder()
        .baseUrl(WEATHER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    // Fetch weather data in a coroutine inside the composable
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            val fetchedWeatherData = weatherApi.getWeather("manila", apiKey)
            weatherData = fetchedWeatherData
        }
    }

    Column ( modifier = Modifier
        .fillMaxWidth()
        .requiredHeight(articleCardSizeHeight)
    ){
        weatherData?.let { data ->
            val iconUrl = "https://openweathermap.org/img/w/${data.weather[0].icon}.png"
            val temperatureInCelsius = (data.main.temp - 273.15).toInt()

            HomeWeatherSection(
                city = data.name,
                temperature = "$temperatureInCelsius°C",
                weatherIcon = rememberAsyncImagePainter(model = iconUrl)
            )
        }
    }
}

@Composable
fun HomeWeatherSection(city: String, temperature: String, weatherIcon: Painter) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(articleCardSizeHeight)
            .background(
                color =
                if (isSystemInDarkTheme()) statusBarColorNight
                else statusBarColorLight
            ),
        verticalArrangement = Arrangement.Top
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingSmall, start = paddingNormal, end = paddingNormal),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = formatDay(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }


            Image(
                painter = weatherIcon,
                contentDescription = null,
                modifier = Modifier.size(iconWeatherSize)
            )
        }


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingSmall, start = paddingNormal, end = paddingNormal),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = weatherDate(),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.tertiary
            )

            Text(
                text = "$temperature $city",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview (showBackground = true)
@Preview (uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeWeatherSectionPreview() {
    FluxNewsTheme {
        HomeWeatherSection(
            city = "Manila",
            temperature = "25°C",
            weatherIcon = painterResource(id = R.drawable.ic_launcher_background)
        )
    }
}