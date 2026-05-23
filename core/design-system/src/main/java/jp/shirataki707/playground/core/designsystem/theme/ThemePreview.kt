package jp.shirataki707.playground.core.designsystem.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview(name = "Color Tokens — Light", showBackground = true, widthDp = 360)
@Preview(name = "Color Tokens — Dark", showBackground = true, widthDp = 360, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ColorTokensPreview() {
    PlaygroundTheme(dynamicColor = false) {
        Surface {
            val colors = PlaygroundTheme.appColors
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                ColorSection(title = "Text") {
                    ColorSwatch(name = "primary", color = colors.text.primary)
                    ColorSwatch(name = "secondary", color = colors.text.secondary)
                    ColorSwatch(name = "tertiary", color = colors.text.tertiary)
                    ColorSwatch(name = "disabled", color = colors.text.disabled)
                    ColorSwatch(name = "link", color = colors.text.link)
                }
                ColorSection(title = "Background") {
                    ColorSwatch(name = "default", color = colors.background.default, bordered = true)
                    ColorSwatch(name = "surface", color = colors.background.surface, bordered = true)
                    ColorSwatch(name = "surfaceVariant", color = colors.background.surfaceVariant)
                    ColorSwatch(name = "inverse", color = colors.background.inverse)
                }
                ColorSection(title = "Border") {
                    ColorSwatch(name = "default", color = colors.border.default)
                    ColorSwatch(name = "variant", color = colors.border.variant)
                }
                ColorSection(title = "Accent") {
                    ColorSwatch(name = "primary", color = colors.accent.primary)
                    ColorSwatch(name = "onPrimary", color = colors.accent.onPrimary)
                    ColorSwatch(name = "container", color = colors.accent.primaryContainer)
                    ColorSwatch(name = "secondary", color = colors.accent.secondary)
                    ColorSwatch(name = "tertiary", color = colors.accent.tertiary)
                }
                ColorSection(title = "Status") {
                    ColorSwatch(name = "success", color = colors.status.success)
                    ColorSwatch(name = "warning", color = colors.status.warning)
                    ColorSwatch(name = "error", color = colors.status.error)
                    ColorSwatch(name = "info", color = colors.status.info)
                }
            }
        }
    }
}

@Preview(name = "Spacing Tokens", showBackground = true, widthDp = 360)
@Composable
private fun SpacingTokensPreview() {
    PlaygroundTheme(dynamicColor = false) {
        Surface {
            val space = PlaygroundTheme.space
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Spacing",
                    style = PlaygroundTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                SpacingSwatch(name = "xxxs", value = space.xxxs)
                SpacingSwatch(name = "xxs", value = space.xxs)
                SpacingSwatch(name = "xs", value = space.xs)
                SpacingSwatch(name = "small", value = space.small)
                SpacingSwatch(name = "medium", value = space.medium)
                SpacingSwatch(name = "large", value = space.large)
                SpacingSwatch(name = "xl", value = space.xl)
                SpacingSwatch(name = "xxl", value = space.xxl)
            }
        }
    }
}

@Preview(name = "Typography Scale", showBackground = true, widthDp = 360)
@Composable
private fun TypographyPreview() {
    PlaygroundTheme(dynamicColor = false) {
        Surface {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                val typography = PlaygroundTheme.typography
                Text(text = "Display Large", style = typography.displayLarge)
                Text(text = "Display Medium", style = typography.displayMedium)
                Text(text = "Display Small", style = typography.displaySmall)
                Text(text = "Headline Large", style = typography.headlineLarge)
                Text(text = "Headline Medium", style = typography.headlineMedium)
                Text(text = "Headline Small", style = typography.headlineSmall)
                Text(text = "Title Large", style = typography.titleLarge)
                Text(text = "Title Medium", style = typography.titleMedium)
                Text(text = "Title Small", style = typography.titleSmall)
                Text(text = "Body Large", style = typography.bodyLarge)
                Text(text = "Body Medium", style = typography.bodyMedium)
                Text(text = "Body Small", style = typography.bodySmall)
                Text(text = "Label Large", style = typography.labelLarge)
                Text(text = "Label Medium", style = typography.labelMedium)
                Text(text = "Label Small", style = typography.labelSmall)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColorSection(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = PlaygroundTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            content()
        }
    }
}

@Composable
private fun ColorSwatch(
    name: String,
    color: Color,
    bordered: Boolean = false,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color = color, shape = PlaygroundTheme.shapes.small)
                .then(
                    if (bordered) {
                        Modifier.border(
                            width = 1.dp,
                            color = PlaygroundTheme.appColors.border.variant,
                            shape = PlaygroundTheme.shapes.small,
                        )
                    } else {
                        Modifier
                    },
                ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = PlaygroundTheme.typography.labelSmall,
        )
    }
}

@Composable
private fun SpacingSwatch(
    name: String,
    value: Dp,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = name,
            style = PlaygroundTheme.typography.labelMedium,
            modifier = Modifier.width(40.dp),
        )
        Box(
            modifier = Modifier
                .width(value)
                .height(20.dp)
                .background(
                    color = PlaygroundTheme.appColors.accent.primary,
                    shape = PlaygroundTheme.shapes.extraSmall,
                ),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${value.value.toInt()}dp",
            style = PlaygroundTheme.typography.labelSmall,
            color = PlaygroundTheme.appColors.text.secondary,
        )
    }
}
