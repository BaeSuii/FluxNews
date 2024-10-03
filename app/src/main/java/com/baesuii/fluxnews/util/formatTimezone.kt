package com.baesuii.fluxnews.util

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import com.baesuii.fluxnews.util.Timezones.REGIONS
import com.baesuii.fluxnews.util.Timezones.TIMEZONE_KEY
import com.baesuii.fluxnews.util.Timezones.timezoneToCityMap
import kotlinx.coroutines.flow.first


fun formatTimezones(): List<String> {
    return REGIONS
}

suspend fun timezoneToCity(dataStore: DataStore<Preferences>): String {
    val preferences = dataStore.data.first()
    val timezoneString = preferences[TIMEZONE_KEY] ?: "GMT +08:00 Manila, PH (Asia)"
    val timezoneIndex = REGIONS.indexOfFirst { it.contains(timezoneString) }
    return timezoneToCityMap[timezoneIndex] ?: "Manila" // Default to "Manila"
}