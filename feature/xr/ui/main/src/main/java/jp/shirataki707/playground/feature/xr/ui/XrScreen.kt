package jp.shirataki707.playground.feature.xr.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.platform.LocalSpatialConfiguration
import jp.shirataki707.playground.core.designsystem.components.PlaygroundTopAppBar
import jp.shirataki707.playground.core.navigation.Navigator
import jp.shirataki707.playground.feature.xr.ui.component.ToggleSpaceModeButton
import jp.shirataki707.playground.feature.xr.ui.layout.SpatialLayout

@Composable
fun XrScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val screenState = rememberXrScreenState(navigator = navigator)
    XrScreen(screenState = screenState, modifier = modifier)
}

@Composable
private fun XrScreen(screenState: XrScreenState, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            PlaygroundTopAppBar(
                title = "",
                showNavigationIcon = true,
                onNavigateBack = screenState.navigateBack,
                actions = {
                    if (LocalSpatialConfiguration.current.hasXrSpatialFeature) {
                        ToggleSpaceModeButton()
                    }
                },
            )
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            Text("XR Screen")
        }
    }
    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
        SpatialLayout(
            primaryContent = {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Primary Content")
                    }
                }
            },
            firstSupportingContent = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(300.dp)
                    ) {
                        Text("First Supporting Content")
                    }
                }
            },
            secondSupportingContent = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Second Supporting Content")
                    }
                }
            },
        )
    }
}
