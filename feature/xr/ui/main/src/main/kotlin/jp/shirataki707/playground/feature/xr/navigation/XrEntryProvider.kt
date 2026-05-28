package jp.shirataki707.playground.feature.xr.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import jp.shirataki707.playground.core.navigation.Navigator
import jp.shirataki707.playground.feature.xr.XrKey
import jp.shirataki707.playground.feature.xr.ui.XrScreen

fun EntryProviderScope<NavKey>.xrEntry(navigator: Navigator) {
    entry<XrKey> {
        XrScreen(navigator = navigator)
    }
}
