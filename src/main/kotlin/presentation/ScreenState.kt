package presentation

import domain.entity.ActuatorState

data class ScreenState(
    val outsideTemp: Float = 0f,
    val insideTemp: Float = 30f,
    val actuatorState: ActuatorState = ActuatorState.Auto
)