package com.baesuii.fluxnews.util

fun cleanContent(content: String?): String {
    if (content == null) return ""

    // Remove "[+X chars]"
    var cleanedContent = content.replace(Regex("\\[\\+\\d+ chars]"), "")

    // Remove common HTML tags
    cleanedContent = cleanedContent.replace(Regex("<ul>|</ul>|<li>|</li>|<p>|</p>|<br>|<br/>"), "")
    cleanedContent = cleanedContent.replace(Regex("<[^>]*>"), "")

    return cleanedContent.trim()
}