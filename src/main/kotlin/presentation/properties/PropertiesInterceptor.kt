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

/**
 * Interceptor to Properties UI for updating data
 */
internal class PropertiesInterceptor(
    getInsideTemperatureUseCase: GetTemperatureUseCase,
    getOutsideTemperatureUseCase: GetTemperatureUseCase,
    getDelayUseCase: GetDelayUseCase,
    getPowerUseCase: GetPowerUseCase,
    getActuatorStateUseCase: GetActuatorStateUseCase,
    getTemperatureActuatorStateUseCase: GetTemperatureActuatorStateUseCase,
    private val updateInsideTemperatureUseCase: UpdateTemperatureUseCase,
    private val updateOutsideTemperatureUseCase: UpdateTemperatureUseCase,
    private val updateDelayUseCase: UpdateDelayUseCase,
    private val updatePowerUseCase: UpdatePowerUseCase,
    private val updateActuatorStateUseCase: UpdateActuatorStateUseCase,
    private val updateTemperatureActuatorStateUseCase: UpdateTemperatureActuatorStateUseCase
) {
    // Properties data
    val state = mutableStateOf(
        PropertiesScreenState(
            insideTemp = getInsideTemperatureUseCase(),
            outsideTemp = getOutsideTemperatureUseCase(),
            delay = getDelayUseCase(),
            actuatorPower = getPowerUseCase(),
            actuatorState = getActuatorStateUseCase(),
            temperatureActuatorState = getTemperatureActuatorStateUseCase()
        )
    )

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
        state.value = state.value.copy(actuatorState = action.state)
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