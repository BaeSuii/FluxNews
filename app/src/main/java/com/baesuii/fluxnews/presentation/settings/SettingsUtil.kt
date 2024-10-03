package com.baesuii.fluxnews.presentation.settings

import android.content.Context
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.AppVersion
import com.baesuii.fluxnews.domain.model.DarkMode
import com.baesuii.fluxnews.domain.model.Timezone

//DarkMode
fun Context.darkMode(
    isDarkModeEnabled: Boolean
) = DarkMode(
    title = getString(R.string.dark_mode),
    description =
    if (isDarkModeEnabled) getString(R.string.dark_mode_on)
    else getString(R.string.dark_mode_off),
    isDarkModeEnabled = isDarkModeEnabled,
    icon =
    if (isDarkModeEnabled) R.drawable.ic_mode_dark
    else R.drawable.ic_mode_light
)


fun Context.timeZone(
    selectedTimezone: String
) = Timezone(
    title = getString(R.string.timezone),
    description = selectedTimezone,
    icon = R.drawable.ic_timezone
)


// App Version
fun Context.appVersion(
    appVersion: String,
) = AppVersion(
    title = getString(R.string.app_version),
    description = appVersion,
    icon = R.drawable.ic_about,
)

fun Context.getAppVersionName(): String = runCatching {
    packageManager.getPackageInfo(packageName, 0).versionName
}.getOrElse { "" }
