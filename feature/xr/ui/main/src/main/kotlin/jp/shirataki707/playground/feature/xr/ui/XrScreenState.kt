package jp.shirataki707.playground.feature.xr.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import jp.shirataki707.playground.core.navigation.Navigator

@Stable
internal class XrScreenState(
    val navigateBack: () -> Unit
)

@Composable
internal fun rememberXrScreenState(
    navigator: Navigator
) : XrScreenState {
    return remember(navigator) {
        XrScreenState(navigator::navigateBack)
    }
}
