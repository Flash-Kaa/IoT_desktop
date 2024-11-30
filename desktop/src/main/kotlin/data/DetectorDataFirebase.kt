package data

import kotlinx.serialization.Serializable

@Serializable
data class DetectorDataFirebase(
    val insideTemperature: Float,
    val outsideTemperature: Float,
    val actuatorState: String,
    val temperatureActuatorState: String,
    val time: Long
)