package jp.shirataki707.playground.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.shirataki707.playground.core.designsystem.theme.PlaygroundTheme

@Suppress("LongParameterList")
@Composable
fun PlaygroundTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = label?.let { { Text(text = it) } },
            placeholder = placeholder?.let { { Text(text = it) } },
            isError = isError,
            enabled = enabled,
            singleLine = true,
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = PlaygroundTheme.appColors.status.error,
                style = PlaygroundTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            )
        }
    }
}

@Preview(name = "TextFields — Light", showBackground = true)
@Preview(name = "TextFields — Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TextFieldsPreview() {
    PlaygroundTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            var text by remember { mutableStateOf("") }
            PlaygroundTextField(
                value = text,
                onValueChange = { text = it },
                label = "Label",
                placeholder = "Placeholder",
                modifier = Modifier.fillMaxWidth(),
            )
            PlaygroundTextField(
                value = "Invalid input",
                onValueChange = {},
                label = "Email",
                isError = true,
                errorMessage = "Please enter a valid email address.",
                modifier = Modifier.fillMaxWidth(),
            )
            PlaygroundTextField(
                value = "Disabled",
                onValueChange = {},
                label = "Disabled field",
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
