package ru.knyazev.coursesapp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onSecondary = ButtonGrayDark,
    tertiary = Pink80,
    onSurface = BackgroundLight,
    onPrimary = GrayLight,
    onBackground  = BackgroundLight,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onSecondary = ButtonGrayLight,
    tertiary = Pink40,
    onBackground = BackgroundDark,
    onSurface = BackgroundDark,
    onPrimary = GrayLight
)

@Composable
fun CoursesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}