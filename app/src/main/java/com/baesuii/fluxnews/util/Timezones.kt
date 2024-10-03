package com.baesuii.fluxnews.util

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

object Timezones {

    val TIMEZONE_KEY: Preferences.Key<String> = stringPreferencesKey("timezone_key")

    val REGIONS = listOf(
        "GMT -11:00 Pago Pago, US (Pacific)",
        "GMT -10:00 Honolulu, US (Pacific)",
        "GMT -09:00 Anchorage, US (America)",
        "GMT -08:00 Los Angeles, US (America)",
        "GMT -07:00 Denver, US (America)",
        "GMT -06:00 Chicago, US (America)",
        "GMT -05:00 New York, US (America)",
        "GMT -04:00 Halifax, CA (America)",
        "GMT -03:30 St. John's, CA (America)",
        "GMT -03:00 Buenos Aires, AR (America)",
        "GMT -01:00 Azores, PT (Atlantic)",
        "GMT +00:00 London, UK (Europe)",
        "GMT +01:00 Berlin, DE (Europe)",
        "GMT +02:00 Athens, GR (Europe)",
        "GMT +03:00 Riyadh, SA (Asia)",
        "GMT +03:30 Tehran, IR (Asia)",
        "GMT +04:00 Dubai, AE (Asia)",
        "GMT +04:30 Kabul, AF (Asia)",
        "GMT +05:00 Karachi, PK (Asia)",
        "GMT +05:30 Kolkata, IN (Asia)",
        "GMT +06:00 Dhaka, BD (Asia)",
        "GMT +07:00 Bangkok, TH (Asia)",
        "GMT +08:00 Manila, PH (Asia)",
        "GMT +09:00 Tokyo, JP (Asia)",
        "GMT +09:30 Darwin, AU (Australia)",
        "GMT +10:00 Sydney, AU (Australia)",
        "GMT +11:00 Noumea, NC (Pacific)",
        "GMT +12:00 Auckland, NZ (Pacific)"
    )

    val timezoneToCityMap = mapOf(
        0 to "Pago Pago",
        1 to "Honolulu",
        2 to "Anchorage",
        3 to "Los Angeles",
        4 to "Denver",
        5 to "Chicago",
        6 to "New York",
        7 to "Halifax",
        8 to "St. John's",
        9 to "Buenos Aires",
        10 to "Azores",
        11 to "London",
        12 to "Berlin",
        13 to "Athens",
        14 to "Riyadh",
        15 to "Tehran",
        16 to "Dubai",
        17 to "Kabul",
        18 to "Karachi",
        19 to "Kolkata",
        20 to "Dhaka",
        21 to "Bangkok",
        22 to "Manila",
        23 to "Tokyo",
        24 to "Darwin",
        25 to "Sydney",
        26 to "Noumea",
        27 to "Auckland"
    )
}