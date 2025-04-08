package dev.jimmymcbride.comments.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val SurfaceLight = Color(0xFFFAFAFA)
val BackgroundLight = Color(0xFFEEEEEE)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val SurfaceDark = Color(0xFF424242)
val BackgroundDark = Color(0xFF212121)

val ColorScheme.avatarBackground: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF4527A0) else Color(0xFFFFE0B2)