package presentation.detectors

import androidx.compose.runtime.mutableStateOf
import domain.usecases.GetDelayUseCase
import domain.usecases.GetPowerUseCase
import domain.usecases.GetTemperatureUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetectorsInterceptor(
    private val getInsideTemperatureUseCase: GetTemperatureUseCase,
    private val getOutsideTemperatureUseCase: GetTemperatureUseCase,
    private val getDelayUseCase: GetDelayUseCase,
    private val getPowerUseCase: GetPowerUseCase
) {
    val state = mutableStateOf(DetectorsScreenState())

    fun startDataCollection(scope: CoroutineScope) {
        scope.launch {
            // Обновление данных с температурными показаниями
            while (true) {
                state.value = state.value.copy(
                    insideTemperature = getInsideTemperatureUseCase(),
                    outsideTemperature = getOutsideTemperatureUseCase(),
                )

                // Задержка согласно полученному времени
                val delayTime = getDelayUseCase()
                delay(delayTime)
            }
        }

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