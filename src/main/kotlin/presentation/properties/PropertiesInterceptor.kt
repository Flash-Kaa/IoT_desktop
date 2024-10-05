package presentation.properties

import androidx.compose.runtime.mutableStateOf
import domain.usecases.GetDelayUseCase
import domain.usecases.GetPowerUseCase
import domain.usecases.GetTemperatureUseCase
import domain.usecases.UpdateDelayUseCase
import domain.usecases.UpdatePowerUseCase
import domain.usecases.UpdateTemperatureUseCase

internal class PropertiesInterceptor(
    getInsideTemperatureUseCase: GetTemperatureUseCase,
    getOutsideTemperatureUseCase: GetTemperatureUseCase,
    getDelayUseCase: GetDelayUseCase,
    getPowerUseCase: GetPowerUseCase,
    private val updateInsideTemperatureUseCase: UpdateTemperatureUseCase,
    private val updateOutsideTemperatureUseCase: UpdateTemperatureUseCase,
    private val updateDelayUseCase: UpdateDelayUseCase,
    private val updatePowerUseCase: UpdatePowerUseCase
) {
    val state = mutableStateOf(
        PropertiesScreenState(
            insideTemp = getInsideTemperatureUseCase(),
            outsideTemp = getOutsideTemperatureUseCase(),
            delay = getDelayUseCase(),
            actuatorPower = getPowerUseCase()
        )
    )

    fun getAction(action: ScreenAction) {
        when (action) {
            is ScreenAction.ChangeInsideTemperature -> changeInside(action)
            is ScreenAction.ChangeOutsideTemperature -> changeOutside(action)
            is ScreenAction.ChangeActuatorState -> changeActuatorState(action)
            is ScreenAction.ChangeActuatorPower -> changeActuatorPower(action)
            is ScreenAction.ChangeDelay -> changeDelay(action)
        }
    }

    private fun changeInside(action: ScreenAction.ChangeInsideTemperature) {
        updateInsideTemperatureUseCase(action.newValue)
        state.value = state.value.copy(insideTemp = action.newValue)
    }

    private fun changeOutside(action: ScreenAction.ChangeOutsideTemperature) {
        updateOutsideTemperatureUseCase(action.newValue)
        state.value = state.value.copy(outsideTemp = action.newValue)
    }

    private fun changeActuatorState(action: ScreenAction.ChangeActuatorState) {
        state.value = state.value.copy(actuatorState = action.actuator)
    }

    private fun changeActuatorPower(action: ScreenAction.ChangeActuatorPower) {
        updatePowerUseCase(action.value)
        state.value = state.value.copy(actuatorPower = action.value)
    }

    private fun changeDelay(action: ScreenAction.ChangeDelay) {
        updateDelayUseCase(action.value)
        state.value = state.value.copy(delay = action.value)
    }
}