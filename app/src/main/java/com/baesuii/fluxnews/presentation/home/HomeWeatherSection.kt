package com.baesuii.fluxnews.presentation.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.WeatherData
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconWeatherSize
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingNormal
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.topBarHeight
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.util.formatDay
import com.baesuii.fluxnews.util.weatherDate

@Composable
fun HomeWeatherBar(
    weatherData: WeatherData?,
    nickname: String,
    selectedEmoji: String,
){

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        weatherData?.let { data ->
            val iconUrl = "https://openweathermap.org/img/w/${data.weather[0].icon}.png"
            val temperatureInCelsius = (data.main.temp - 273.15).toInt()

            HomeWeatherSection(
                city = data.name,
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
    temperature: String,
    weatherIcon: Painter,
    nickname: String,
    selectedEmoji: String
) {

    val greeting = formatDay()

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
                Text(
                    text = personalizedGreeting,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(paddingExtraSmall))

                if (nickname.isNotEmpty()) {
                    Text(
                        text = "$selectedEmoji $nickname",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
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
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeWeatherSectionPreview() {
    FluxNewsTheme (
        dynamicColor = false
    ){
        HomeWeatherSection(
            city = "Manila",
            temperature = "25°C",
            weatherIcon = painterResource(id = R.drawable.ic_launcher_background),
            nickname =  "Bea",
            selectedEmoji = "\uD83D\uDE36"
        )
    }
}