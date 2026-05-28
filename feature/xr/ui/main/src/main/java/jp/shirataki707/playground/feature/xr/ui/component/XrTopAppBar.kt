package jp.shirataki707.playground.feature.xr.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.xr.compose.spatial.ContentEdge
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterOffsetType
import jp.shirataki707.playground.core.designsystem.theme.PlaygroundTheme

@Suppress("DEPRECATION")
@Composable
internal fun XrTopAppBar() {
    Orbiter(
        position = ContentEdge.Top,
        alignment = Alignment.End,
        offset = PlaygroundTheme.space.medium,
        offsetType = OrbiterOffsetType.InnerEdge,
    ) {
        ToggleSpaceModeButton()
    }
}
