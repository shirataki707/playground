package jp.shirataki707.playground.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.shirataki707.playground.core.designsystem.theme.PlaygroundTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaygroundTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        navigationIcon = navigationIcon ?: {},
        actions = actions,
    )
}

@Preview(name = "TopAppBar — without nav icon", showBackground = true)
@Composable
private fun TopAppBarSimplePreview() {
    PlaygroundTheme {
        PlaygroundTopAppBar(title = "Screen Title")
    }
}

@Preview(name = "TopAppBar — with nav icon", showBackground = true)
@Preview(name = "TopAppBar — Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TopAppBarWithNavPreview() {
    PlaygroundTheme {
        PlaygroundTopAppBar(
            title = "Detail Screen",
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            },
        )
    }
}
