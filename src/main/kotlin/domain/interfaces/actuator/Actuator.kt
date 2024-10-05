package domain.interfaces.actuator

import data.Place
import domain.entity.ActuatorState
import kotlinx.coroutines.flow.Flow

abstract class Actuator(
    private val place: Place
) {
    abstract val actuatorOn: Flow<ActuatorState>

    abstract fun getPower(): Float

    abstract fun updatePower(value: Float)
}