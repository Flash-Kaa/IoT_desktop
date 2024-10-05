package presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import presentation.detectors.DetectorsFragment
import presentation.di.DiUseCases
import presentation.properties.PropertiesFragment

private val propInterceptor = DiUseCases.propertiesInterceptor
private val detectInterceptor = DiUseCases.detectorsInterceptor

@Composable
fun MainActivity() {
    val coroutineScope = rememberCoroutineScope()
    Row {
        Box(modifier = Modifier.weight(3f)) {
            PropertiesFragment(
                screenState = propInterceptor.state,
                screenAction = propInterceptor::getAction
            )
        }

        Box(modifier = Modifier.weight(2f)) {
            LaunchedEffect(Unit) {
                detectInterceptor.startDataCollection(coroutineScope)
            }

            DetectorsFragment(
                screenState = detectInterceptor.state
            )
        }
    }
}