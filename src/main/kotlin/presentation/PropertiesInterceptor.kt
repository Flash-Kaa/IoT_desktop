package presentation

import androidx.compose.runtime.mutableStateOf

internal class PropertiesInterceptor {
    val state = mutableStateOf(ScreenState())

    fun getAction(action: ScreenAction) {
        when (action) {
            is ScreenAction.ChangeInsideTemperature -> changeInside(action)
            is ScreenAction.ChangeOutsideTemperature -> changeOutside(action)
            is ScreenAction.ChangeActuatorState -> changeActuatorState(action)
        }
    }

    private fun changeInside(action: ScreenAction.ChangeInsideTemperature) {
        state.value = state.value.copy(insideTemp = action.newValue)
    }

    private fun changeOutside(action: ScreenAction.ChangeOutsideTemperature) {
        state.value = state.value.copy(outsideTemp = action.newValue)
    }

    private fun changeActuatorState(action: ScreenAction.ChangeActuatorState) {
        state.value = state.value.copy(actuatorState = action.actuator)
    }
}