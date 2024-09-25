import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.TestRepositoryImpl
import domain.TestRepository

@Composable
@Preview
fun App() {
    val repo: TestRepository by remember { mutableStateOf(TestRepositoryImpl()) }
    var text by remember {
        mutableStateOf(repo.getData())
    }

    MaterialTheme {
        Button(onClick = {
            text = 1234
        }) {
            Text(text.toString())
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
