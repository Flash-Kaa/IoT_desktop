package presentation.properties

import domain.entity.ActuatorState
import domain.entity.TemperatureActuatorState

/**
 * Data for properties UI
 */
data class PropertiesScreenState(
    val outsideTemp: Float = 0f,
    val insideTemp: Float = 30f,
    val actuatorPower: Float = 0.1f,
    val actuatorState: ActuatorState = ActuatorState.Auto,
    val temperatureActuatorState: TemperatureActuatorState = TemperatureActuatorState.None,
    val delay: Long = 100L
)