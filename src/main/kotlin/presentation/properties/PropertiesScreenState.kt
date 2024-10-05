package presentation.properties

import domain.entity.ActuatorState

data class PropertiesScreenState(
    val outsideTemp: Float = 0f,
    val insideTemp: Float = 30f,
    val actuatorPower: Float = 0.1f,
    val actuatorState: ActuatorState = ActuatorState.Auto,
    val delay: Long = 100L
)