package presentation.detectors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * UI data from scanners
 */
@Composable
fun DetectorsFragment(
    screenState: MutableState<DetectorsScreenState>
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color(197, 191, 184))
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Detectors data",
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(32.dp))

        // Outside temperature data
        Text(
            text = String.format("Outside t (°C): %.4f", screenState.value.outsideTemperature),
            fontSize = 22.sp,
        )

        Spacer(Modifier.height(24.dp))

        // Inside temperature data
        Text(
            text = String.format("Inside t (°C): %.4f", screenState.value.insideTemperature),
            fontSize = 22.sp,
        )

        Spacer(Modifier.height(24.dp))

        // Delay value
        Text(
            text = "Delay (ms): ${screenState.value.delay}",
            fontSize = 22.sp,
        )

        Spacer(Modifier.height(24.dp))

        // Actuator power value
        Text(
            text = String.format("Power (°C/s): %.2f", screenState.value.actuatorPower),
            fontSize = 22.sp,
        )

        Spacer(Modifier.height(24.dp))

        // Temperature actuator state
        Text(
            text = "Temperature actuator state: ${screenState.value.temperatureActuatorState.name}",
            fontSize = 22.sp,
        )
    }
}