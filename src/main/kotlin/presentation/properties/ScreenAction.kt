package presentation.properties

import domain.entity.ActuatorState

internal sealed class ScreenAction {
    data class ChangeOutsideTemperature(val newValue: Float): ScreenAction()
    data class ChangeInsideTemperature(val newValue: Float): ScreenAction()
    data class ChangeActuatorState(val actuator: ActuatorState): ScreenAction()
    data class ChangeActuatorPower(val value: Float): ScreenAction()
    data class ChangeDelay(val value: Long): ScreenAction()
}