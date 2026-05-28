package jp.shirataki707.playground.feature.xr.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.platform.LocalSpatialConfiguration
import jp.shirataki707.playground.feature.xr.XrUiDrawable

@Composable
internal fun ToggleSpaceModeButton(modifier: Modifier = Modifier) {
    val spatialConfiguration = LocalSpatialConfiguration.current

    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
        ToggleSpaceModeButton(
            modifier = modifier,
            contentDescription = "ホームスペースモードに変更",
            iconResourceId = XrUiDrawable.ic_home_space_mode,
            onClick = { spatialConfiguration.requestHomeSpaceMode() }
        )
    } else {
        ToggleSpaceModeButton(
            modifier = modifier,
            contentDescription = "フルスペースモードに変更",
            iconResourceId = XrUiDrawable.ic_full_space_mode,
            onClick = { spatialConfiguration.requestFullSpaceMode() }
        )
    }
}

@Composable
private fun ToggleSpaceModeButton(
    contentDescription: String,
    @DrawableRes iconResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledTonalIconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(iconResourceId),
            contentDescription = contentDescription,
        )
    }
}
