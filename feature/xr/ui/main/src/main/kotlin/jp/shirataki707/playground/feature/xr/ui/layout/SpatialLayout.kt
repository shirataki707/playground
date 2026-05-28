package jp.shirataki707.playground.feature.xr.ui.layout

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.ResizePolicy
import androidx.xr.compose.subspace.SpatialColumn
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.SpatialRow
import androidx.xr.compose.subspace.draw.alpha
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.fillMaxSize
import androidx.xr.compose.subspace.layout.fillMaxWidth
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.padding
import androidx.xr.compose.subspace.layout.transformingMovable
import androidx.xr.compose.subspace.layout.width
import jp.shirataki707.playground.core.theme.PlaygroundTheme
import jp.shirataki707.playground.feature.xr.ui.component.XrTopAppBar
import kotlinx.coroutines.launch

@Composable
internal fun SpatialLayout(
    primaryContent: @Composable () -> Unit,
    firstSupportingContent: @Composable () -> Unit,
    secondSupportingContent: @Composable () -> Unit,
) {
    val animatedAlpha = remember { Animatable(initialValue = 0.5f) }
    LaunchedEffect(Unit) {
        launch {
            animatedAlpha.animateTo(
                targetValue = 1.0f,
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
            )
        }
    }

    Subspace {
        SpatialRow(modifier = SubspaceModifier.height(816.dp).fillMaxWidth()) {
            SpatialColumn(modifier = SubspaceModifier.width(400.dp)) {
                SpatialPanel(
                    modifier = SubspaceModifier
                        .alpha(alpha = animatedAlpha.value)
                        .fillMaxWidth()
                        .padding(bottom = PlaygroundTheme.space.medium)
                        .transformingMovable(),
                    resizePolicy = ResizePolicy(),
                ) {
                    firstSupportingContent()
                }
                SpatialPanel(
                    modifier = SubspaceModifier
                        .alpha(alpha = animatedAlpha.value)
                        .fillMaxWidth()
                        .weight(weight = 1f)
                        .transformingMovable(),
                    resizePolicy = ResizePolicy(),
                ) {
                    secondSupportingContent()
                }
            }
            SpatialPanel(
                modifier = SubspaceModifier
                    .alpha(alpha = animatedAlpha.value)
                    .fillMaxSize()
                    .padding(start = PlaygroundTheme.space.medium)
                    .transformingMovable(),
                resizePolicy = ResizePolicy(),
            ) {
                primaryContent()
                XrTopAppBar()
            }
        }
    }
}
