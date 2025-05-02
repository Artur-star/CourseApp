package ru.knyazev.coursesapp.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

private val DarkColorScheme = darkColorScheme(
    background = BackgroundDark,
    onBackground = WhiteMainDark,
    primary = GrayDark,
    onPrimary = WhiteDark,
    secondary = StrokeGreyDark,
    surface = ButtonGrayDark,
)

private val LightColorScheme = lightColorScheme(
    background = BackgroundLight,
    onBackground = BlackMainLight,
    primary = GrayLight,
    onPrimary = BlackLight,
    secondary = StrokeGreyLight,
    surface = ButtonGrayLight,
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
        content = {
            ProvideTextStyle(
                value = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                content = content
            )
        }
    )
}