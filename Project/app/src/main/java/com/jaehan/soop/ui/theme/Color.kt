package com.jaehan.soop.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val skyBlue = Color(0xFF4FC3F7)
val lightGray = Color(0xFFF5F5F5)

val languageColors = mapOf(
    "Kotlin" to Color(0xFFA97BFF),
    "Java" to Color(0xFFB07219),
    "Python" to Color(0xFF3572A5),
    "JavaScript" to Color(0xFFF1E05A),
    "C++" to Color(0xFFF34B7D),
    "HTML" to Color(0xFFE34C26),
    "CSS" to Color(0xFF563D7C),
    "Swift" to Color(0xFFF05138),
    "Ruby" to Color(0xFF701516),
    "C" to Color(0xFF555555),
    "C#" to Color(0xFF178600),
    "TypeScript" to Color(0xFF3178c6),
)

fun getLanguageColor(language: String): Color {
    return languageColors[language] ?: Color.Black
}
