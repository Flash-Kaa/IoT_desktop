package presentation

import domain.entity.ActuatorState

internal sealed class ScreenAction {
    data class ChangeOutsideTemperature(val newValue: Float): ScreenAction()
    data class ChangeInsideTemperature(val newValue: Float): ScreenAction()
    data class ChangeActuatorState(val actuator: ActuatorState): ScreenAction()
}