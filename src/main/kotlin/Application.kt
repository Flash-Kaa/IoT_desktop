import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import presentation.MainActivity


// Start application
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "IoT Lab_1"
    ) {
        MainActivity()
    }
}
