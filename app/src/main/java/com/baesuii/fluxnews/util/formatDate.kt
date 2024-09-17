package com.baesuii.fluxnews.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Date
import java.util.TimeZone

fun formatDate(dateString: String): String {
    return try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        originalFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date? = originalFormat.parse(dateString)

        if (date != null) {
            val targetFormat = SimpleDateFormat("HH:mm - MMM dd, yyyy", Locale.getDefault())
            targetFormat.format(date)
        } else {
            dateString
        }
    } catch (e: Exception) {
        dateString
    }
}

fun weatherDate(): String {
    val originalFormat = SimpleDateFormat("EEE dd MMM, yyyy", Locale.getDefault())
    originalFormat.timeZone = TimeZone.getTimeZone("UTC")
    return originalFormat.format(Calendar.getInstance().time)
}