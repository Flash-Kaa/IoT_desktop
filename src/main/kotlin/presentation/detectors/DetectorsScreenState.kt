package presentation.detectors

import domain.entity.TemperatureActuatorState

data class DetectorsScreenState(
    val insideTemperature: Float = 20f,
    val outsideTemperature: Float = 20f,
    val delay: Long = 1000L,
    val actuatorPower: Float = .1f,
    val temperatureActuatorState: TemperatureActuatorState = TemperatureActuatorState.None
)
