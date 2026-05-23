package jp.shirataki707.playground.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class PlaygroundColors(
    val text: TextColors,
    val background: BackgroundColors,
    val surface: SurfaceColors,
    val border: BorderColors,
    val accent: AccentColors,
    val status: StatusColors,
)

@Immutable
data class TextColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val disabled: Color,
    val onPrimary: Color,
    val link: Color,
)

@Immutable
data class BackgroundColors(
    val default: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val inverse: Color,
)

@Immutable
data class SurfaceColors(
    val default: Color,
    val variant: Color,
    val container: Color,
    val inverse: Color,
)

@Immutable
data class BorderColors(
    val default: Color,
    val variant: Color,
)

@Immutable
data class AccentColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
)

@Immutable
data class StatusColors(
    val success: Color,
    val warning: Color,
    val error: Color,
    val info: Color,
)

internal fun playgroundColors(
    colorScheme: ColorScheme,
    isLight: Boolean,
): PlaygroundColors =
    PlaygroundColors(
        text =
            TextColors(
                primary = colorScheme.onBackground,
                secondary = colorScheme.onSurfaceVariant,
                tertiary = colorScheme.outline,
                disabled = colorScheme.onSurface.copy(alpha = 0.38f),
                onPrimary = colorScheme.onPrimary,
                link = colorScheme.primary,
            ),
        background =
            BackgroundColors(
                default = colorScheme.background,
                surface = colorScheme.surface,
                surfaceVariant = colorScheme.surfaceVariant,
                inverse = colorScheme.inverseSurface,
            ),
        surface =
            SurfaceColors(
                default = colorScheme.surface,
                variant = colorScheme.surfaceVariant,
                container = colorScheme.primaryContainer,
                inverse = colorScheme.inverseSurface,
            ),
        border =
            BorderColors(
                default = colorScheme.outline,
                variant = colorScheme.outlineVariant,
            ),
        accent =
            AccentColors(
                primary = colorScheme.primary,
                onPrimary = colorScheme.onPrimary,
                primaryContainer = colorScheme.primaryContainer,
                secondary = colorScheme.secondary,
                onSecondary = colorScheme.onSecondary,
                tertiary = colorScheme.tertiary,
                onTertiary = colorScheme.onTertiary,
            ),
        status =
            StatusColors(
                success = if (isLight) Color(0xFF146C2E) else Color(0xFF7BC99C),
                warning = if (isLight) Color(0xFFE65100) else Color(0xFFFFB74D),
                error = colorScheme.error,
                info = if (isLight) Color(0xFF006494) else Color(0xFF82CFFF),
            ),
    )
