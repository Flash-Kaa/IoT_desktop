package presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.detectors.DetectorsFragment
import presentation.properties.PropertiesFragment

private val interceptor = PropertiesInterceptor()

@Composable
fun MainActivity() {
    Row {
        Box(modifier = Modifier.weight(3f)){
            PropertiesFragment(
                screenState = interceptor.state,
                screenAction = interceptor::getAction
            )
        }

        Box(modifier = Modifier.weight(2f)){
            DetectorsFragment()
        }
    }
}