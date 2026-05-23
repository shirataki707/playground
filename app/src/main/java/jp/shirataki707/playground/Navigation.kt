package jp.shirataki707.playground

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import jp.shirataki707.playground.core.navigation.Navigator
import jp.shirataki707.playground.core.navigation.rememberNavigationState
import jp.shirataki707.playground.core.navigation.toEntries
import jp.shirataki707.playground.feature.xr.xrEntry
import jp.shirataki707.playground.ui.main.MainScreen

@Composable
fun MainNavigation() {
    val navState =
        rememberNavigationState(
            startKey = Main,
            topLevelKeys = setOf(Main),
        )
    val navigator = remember { Navigator(navState) }

    val navEntryProvider =
        entryProvider {
            entry<Main> {
                MainScreen(
                    onItemClick = { navigator.navigate(it) },
                    modifier =
                        Modifier
                            .safeDrawingPadding()
                            .padding(16.dp),
                )
            }
            xrEntry(navigator)
        }

    NavDisplay(
        entries = navState.toEntries(navEntryProvider),
        onBack = { if (navState.currentKey != navState.startKey) navigator.goBack() },
    )
}
