package com.baesuii.fluxnews.presentation.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.WeatherData
import com.baesuii.fluxnews.presentation.common.WeatherText
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconWeatherSize
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingNormal
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.topBarHeight
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.util.Timezones.REGIONS
import com.baesuii.fluxnews.util.Timezones.timezoneToCityMap
import com.baesuii.fluxnews.util.formatDay
import com.baesuii.fluxnews.util.weatherDate

@Composable
fun HomeWeatherBar(
    weatherData: WeatherData?,
    timezoneOffset: Int,
    timezone: String,
    nickname: String,
    selectedEmoji: String
){

    val timezoneIndex = if (timezone == "GMT") {
        22 // Default to Manila
    } else {
        REGIONS.indexOfFirst { it.contains(timezone) }
    }
    val city = timezoneToCityMap[timezoneIndex] ?: "Manila"
    println("Timezone: $timezone, Timezone Offset: $timezoneOffset")
    println("Index: $timezoneIndex, City: $city")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        weatherData?.let { data ->
            val iconUrl = "https://openweathermap.org/img/w/${data.weather[0].icon}.png"
            val temperatureInCelsius = (data.main.temp - 273.15).toInt()

            HomeWeatherSection(
                city = city,
                timezoneOffset = timezoneOffset,
                temperature = "$temperatureInCelsius°C",
                weatherIcon = rememberAsyncImagePainter(model = iconUrl),
                nickname = nickname,
                selectedEmoji = selectedEmoji
            )
        }
    }
}

@Composable
fun HomeWeatherSection(
    city: String,
    timezoneOffset: Int,
    temperature: String,
    weatherIcon: Painter,
    nickname: String,
    selectedEmoji: String
) {

    val greeting = formatDay(timezoneOffset)

    val personalizedGreeting = if (nickname.isEmpty()) {
        "$greeting!"
    } else {
        "$greeting,"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(topBarHeight)
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Bottom
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingSmall, start = paddingNormal, end = paddingNormal),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                WeatherText(text = personalizedGreeting)

                Spacer(modifier = Modifier.height(paddingExtraSmall))

                if (nickname.isNotEmpty()) {
                    WeatherText(text = "$selectedEmoji $nickname")
                }
            }


            Image(
                painter = weatherIcon,
                contentDescription = null,
                modifier = Modifier.size(iconWeatherSize)
            )
        }
        Spacer(modifier = Modifier.height(paddingExtraSmall))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingSmall, start = paddingNormal, end = paddingNormal),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = weatherDate(timezoneOffset),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.tertiary
            )

            WeatherText(text = "$temperature $city")
        }
    }
}

@Preview (showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeWeatherSectionPreview() {
    FluxNewsTheme (
        dynamicColor = false
    ){
        HomeWeatherSection(
            city = "Manila",
            timezoneOffset = 28800,
            temperature = "25°C",
            weatherIcon = painterResource(id = R.drawable.ic_launcher_background),
            nickname =  "Bea",
            selectedEmoji = "\uD83D\uDE36"
        )
    }
}