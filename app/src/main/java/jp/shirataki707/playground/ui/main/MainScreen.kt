package jp.shirataki707.playground.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import jp.shirataki707.playground.core.theme.PlaygroundTheme
import jp.shirataki707.playground.data.DefaultDataRepository
import jp.shirataki707.playground.feature.xr.XrKey

@Composable
fun MainScreen(
    onItemClick: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = viewModel { MainScreenViewModel(DefaultDataRepository()) },
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    when (state) {
        MainScreenUiState.Loading -> {
            // Blank
        }

        is MainScreenUiState.Success -> {
            MainScreen(
                data = (state as MainScreenUiState.Success).data,
                onXrClick = { onItemClick(XrKey) },
                modifier = modifier,
            )
        }

        is MainScreenUiState.Error -> {
            Text("Error loading data: ${(state as MainScreenUiState.Error).throwable.message}")
        }
    }
}

@Composable
internal fun MainScreen(
    data: List<String>,
    onXrClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        data.forEach { Greeting(it) }
        Button(onClick = onXrClick) {
            Text("Open XR")
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PlaygroundTheme { MainScreen(data = listOf("Android"), onXrClick = {}) }
}

@Preview(showBackground = true, widthDp = 340)
@Composable
fun MainScreenPortraitPreview() {
    PlaygroundTheme { MainScreen(data = listOf("Android"), onXrClick = {}) }
}
