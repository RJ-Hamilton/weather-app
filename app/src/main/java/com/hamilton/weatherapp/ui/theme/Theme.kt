package com.hamilton.weatherapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = OceanTeal,
    secondary = OceanTeal,
    tertiary = Black,
    background = OffWhite,
    surface = OffWhite,
    onPrimary = Black,
    onSecondary = Black,
    onSurface = Black
)

private val LightColorScheme = lightColorScheme(
    primary = OceanTeal,
    secondary = OffWhite,
    tertiary = Black,
    background = OffWhite,
    surface = OffWhite,
    onPrimary = Black,
    onSecondary = Black,
    onSurface = Black
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if(!darkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}