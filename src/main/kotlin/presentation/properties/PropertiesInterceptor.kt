package presentation.properties

import androidx.compose.runtime.mutableStateOf
import domain.entity.TemperatureActuatorState
import domain.usecases.GetActuatorStateUseCase
import domain.usecases.GetDelayUseCase
import domain.usecases.GetPowerUseCase
import domain.usecases.GetTemperatureActuatorStateUseCase
import domain.usecases.GetTemperatureUseCase
import domain.usecases.UpdateActuatorStateUseCase
import domain.usecases.UpdateDelayUseCase
import domain.usecases.UpdatePowerUseCase
import domain.usecases.UpdateTemperatureActuatorStateUseCase
import domain.usecases.UpdateTemperatureUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Interceptor to Properties UI for updating data
 */
internal class PropertiesInterceptor(
    private val getActuatorStateUseCase: GetActuatorStateUseCase,

    private val getInsideTemperatureUseCase: GetTemperatureUseCase,
    private val getOutsideTemperatureUseCase: GetTemperatureUseCase,
    private val getDelayUseCase: GetDelayUseCase,
    private val getPowerUseCase: GetPowerUseCase,
    private val getTemperatureActuatorStateUseCase: GetTemperatureActuatorStateUseCase,
    private val updateInsideTemperatureUseCase: UpdateTemperatureUseCase,
    private val updateOutsideTemperatureUseCase: UpdateTemperatureUseCase,
    private val updateDelayUseCase: UpdateDelayUseCase,
    private val updatePowerUseCase: UpdatePowerUseCase,
    private val updateActuatorStateUseCase: UpdateActuatorStateUseCase,
    private val updateTemperatureActuatorStateUseCase: UpdateTemperatureActuatorStateUseCase
) {
    // Properties data
    val state = mutableStateOf(PropertiesScreenState())

    fun startDataCollection(scope: CoroutineScope) {
        // Running coroutine to update the data
        scope.launch {
            // Updating data with temperature readings
            while (true) {
                state.value = state.value.copy(
                    insideTemp = getInsideTemperatureUseCase(),
                    outsideTemp = getOutsideTemperatureUseCase(),
                    temperatureActuatorState = getTemperatureActuatorStateUseCase(),
                    actuatorState = getActuatorStateUseCase()
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

    // Action interceptor
    fun getAction(action: ScreenAction) {
        when (action) {
            is ScreenAction.ChangeInsideTemperature -> changeInside(action)
            is ScreenAction.ChangeOutsideTemperature -> changeOutside(action)
            is ScreenAction.ChangeActuatorState -> changeActuatorState(action)
            is ScreenAction.ChangeActuatorPower -> changeActuatorPower(action)
            is ScreenAction.ChangeDelay -> changeDelay(action)
            is ScreenAction.ChangeTemperatureActuatorState -> changeTemperatureActuatorState(action)
        }
    }

    private fun changeInside(action: ScreenAction.ChangeInsideTemperature) {
        updateInsideTemperatureUseCase(action.value)
        state.value = state.value.copy(insideTemp = action.value)
    }

    private fun changeOutside(action: ScreenAction.ChangeOutsideTemperature) {
        updateOutsideTemperatureUseCase(action.value)
        state.value = state.value.copy(outsideTemp = action.value)
    }

    private fun changeActuatorState(action: ScreenAction.ChangeActuatorState) {
        updateActuatorStateUseCase(action.state)
        updateTemperatureActuatorStateUseCase(TemperatureActuatorState.None)
        state.value = state.value.copy(
            actuatorState = action.state,
            temperatureActuatorState = TemperatureActuatorState.None
        )
    }

    private fun changeActuatorPower(action: ScreenAction.ChangeActuatorPower) {
        updatePowerUseCase(action.value)
        state.value = state.value.copy(actuatorPower = action.value)
    }

    private fun changeDelay(action: ScreenAction.ChangeDelay) {
        updateDelayUseCase(action.value)
        state.value = state.value.copy(delay = action.value)
    }

    private fun changeTemperatureActuatorState(action: ScreenAction.ChangeTemperatureActuatorState) {
        updateTemperatureActuatorStateUseCase(action.state)
        state.value = state.value.copy(temperatureActuatorState = action.state)
    }
}