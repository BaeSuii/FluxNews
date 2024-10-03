package com.baesuii.fluxnews.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun formatDate(dateString: String): String {
    return try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        originalFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date? = originalFormat.parse(dateString)

        if (date != null) {
            val now = Date()
            val diffInMillis = now.time - date.time

            val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
            val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)

            when {
                minutes < 1 -> "Just now"
                minutes == 1L -> "1 minute ago"
                minutes < 60 -> "$minutes minutes ago"
                hours == 1L -> "1 hour ago"
                hours < 24 -> "$hours hours ago"
                else -> {
                    val fullDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    fullDateFormat.format(date)
                }
            }
        } else {
            dateString
        }
    } catch (e: Exception) {
        dateString
    }
}

fun weatherDate(timezoneOffset: Int): String {
    val utcTimeInMillis = System.currentTimeMillis()
    val localTimeInMillis = utcTimeInMillis + (timezoneOffset * 1000L)

    val originalFormat = SimpleDateFormat("EEE dd MMM, yyyy", Locale.getDefault())
    originalFormat.timeZone = TimeZone.getTimeZone("UTC")
    return originalFormat.format(localTimeInMillis)
}