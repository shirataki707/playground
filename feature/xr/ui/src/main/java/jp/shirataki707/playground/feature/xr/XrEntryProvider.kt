package jp.shirataki707.playground.feature.xr

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import jp.shirataki707.playground.core.navigation.Navigator

fun EntryProviderScope<NavKey>.xrEntry(navigator: Navigator) {
    entry<XrKey> {
        XrScreen(navigator = navigator)
    }
}
