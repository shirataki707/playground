package jp.shirataki707.playground.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.shirataki707.playground.core.designsystem.theme.PlaygroundTheme

@Composable
fun PlaygroundCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (onClick != null) {
        ElevatedCard(
            onClick = onClick,
            modifier = modifier,
        ) {
            Column(modifier = Modifier.padding(16.dp), content = content)
        }
    } else {
        Card(modifier = modifier) {
            Column(modifier = Modifier.padding(16.dp), content = content)
        }
    }
}

@Preview(name = "Cards — Light", showBackground = true)
@Preview(name = "Cards — Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CardsPreview() {
    PlaygroundTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            PlaygroundCard(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Static Card", style = PlaygroundTheme.typography.titleMedium)
                Text(text = "Non-clickable card content.", style = PlaygroundTheme.typography.bodyMedium)
            }
            PlaygroundCard(modifier = Modifier.fillMaxWidth(), onClick = {}) {
                Text(text = "Elevated Card", style = PlaygroundTheme.typography.titleMedium)
                Text(text = "Clickable elevated card content.", style = PlaygroundTheme.typography.bodyMedium)
            }
        }
    }
}
