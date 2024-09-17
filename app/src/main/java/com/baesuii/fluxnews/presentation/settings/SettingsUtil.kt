package com.baesuii.fluxnews.presentation.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.baesuii.fluxnews.R

// Light and Night Mode Theme
fun Int.getThemeName(context: Context): String = when (this) {
    MODE_NIGHT_FOLLOW_SYSTEM -> context.getString(R.string.use_system_settings)
    MODE_NIGHT_NO -> context.getString(R.string.light_mode)
    MODE_NIGHT_YES -> context.getString(R.string.dark_mode)
    else -> context.getString(R.string.use_system_settings)
}

// App Version
fun Context.getAppVersionName(): String = runCatching {
    packageManager.getPackageInfo(packageName, 0).versionName
}.getOrElse { "" }
