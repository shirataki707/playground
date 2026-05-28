package jp.shirataki707.playground.feature.xr.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.platform.LocalSpatialConfiguration
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun XrScreen(screenState: XrScreenState, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = screenState.navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    if (LocalSpatialConfiguration.current.hasXrSpatialFeature) {
                        ToggleSpaceModeButton()
                    }
                },
            )
        },
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
        XrSpatialLayout()
    }
}

@Composable
private fun XrSpatialLayout() {
    SpatialLayout(
        primaryContent = {
            Surface(modifier = Modifier.fillMaxSize()) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("Primary Content")
                }
            }
        },
        firstSupportingContent = {
            Surface(modifier = Modifier.fillMaxWidth()) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(300.dp)) {
                    Text("First Supporting Content")
                }
            }
        },
        secondSupportingContent = {
            Surface(modifier = Modifier.fillMaxWidth()) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("Second Supporting Content")
                }
            }
        },
    )
}
