package presentation.properties

import domain.entity.ActuatorState
import domain.entity.TemperatureActuatorState

/**
 * Actions for changing properties screen
 */
internal sealed class ScreenAction {
    data class ChangeOutsideTemperature(val value: Float): ScreenAction()
    data class ChangeInsideTemperature(val value: Float): ScreenAction()
    data class ChangeActuatorState(val state: ActuatorState): ScreenAction()
    data class ChangeTemperatureActuatorState(val state: TemperatureActuatorState): ScreenAction()
    data class ChangeActuatorPower(val value: Float): ScreenAction()
    data class ChangeDelay(val value: Long): ScreenAction()
}