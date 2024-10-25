package presentation.detectors

import androidx.compose.runtime.mutableStateOf
import domain.usecases.GetDelayUseCase
import domain.usecases.GetPowerUseCase
import domain.usecases.GetTemperatureActuatorStateUseCase
import domain.usecases.GetTemperatureUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Interceptor to Detectors UI for getting data
 */
class DetectorsInterceptor(
    private val getInsideTemperatureUseCase: GetTemperatureUseCase,
    private val getOutsideTemperatureUseCase: GetTemperatureUseCase,
    private val getTemperatureActuatorStateUseCase: GetTemperatureActuatorStateUseCase,
    private val getDelayUseCase: GetDelayUseCase,
    private val getPowerUseCase: GetPowerUseCase
) {
    // Detectors data
    val state = mutableStateOf(DetectorsScreenState())

    fun startDataCollection(scope: CoroutineScope) {
        // Running coroutine to update the data
        scope.launch {
            // Updating data with temperature readings
            while (true) {
                state.value = state.value.copy(
                    insideTemperature = getInsideTemperatureUseCase(),
                    outsideTemperature = getOutsideTemperatureUseCase(),
                    temperatureActuatorState = getTemperatureActuatorStateUseCase()
                )

                // Delay according to the received time
                val delayTime = getDelayUseCase()
                delay(delayTime)
            }
        }

        // Running coroutine to update delay and actuator power
        scope.launch {
            while (true) {
                state.value = state.value.copy(
                    delay = getDelayUseCase(),
                    actuatorPower = getPowerUseCase()
                )

                delay(150L)
            }
        }
    }
}