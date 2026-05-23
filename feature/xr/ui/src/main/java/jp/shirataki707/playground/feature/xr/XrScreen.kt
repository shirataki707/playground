package jp.shirataki707.playground.feature.xr

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import jp.shirataki707.playground.core.navigation.Navigator

@Suppress("UnusedParameter")
@Composable
fun XrScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    XrScreen(modifier = modifier)
}

@Composable
internal fun XrScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Text("XR Screen")
    }
}
