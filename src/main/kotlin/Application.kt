import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import presentation.MainActivity


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MainActivity()
    }
}
