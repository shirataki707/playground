package jp.shirataki707.playground.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme =
    lightColorScheme(
        primary = Color(0xFF6650A4),
        secondary = Color(0xFF625B71),
        tertiary = Color(0xFF7D5260),
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = Color(0xFFD0BCFF),
        secondary = Color(0xFFCCC2DC),
        tertiary = Color(0xFFEFB8C8),
    )

internal val LocalPlaygroundColors =
    compositionLocalOf<PlaygroundColors> {
        error("PlaygroundColors not provided — wrap your UI with PlaygroundTheme")
    }

internal val LocalPlaygroundSpacing = compositionLocalOf { DefaultPlaygroundSpacing }

@Composable
fun PlaygroundTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme: ColorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    val playgroundColors = playgroundColors(colorScheme = colorScheme, isLight = !darkTheme)

    CompositionLocalProvider(
        LocalPlaygroundColors provides playgroundColors,
        LocalPlaygroundSpacing provides DefaultPlaygroundSpacing,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

object PlaygroundTheme {
    val appColors: PlaygroundColors
        @Composable
        @ReadOnlyComposable
        get() = LocalPlaygroundColors.current

    val space: PlaygroundSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalPlaygroundSpacing.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes

    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme
}
