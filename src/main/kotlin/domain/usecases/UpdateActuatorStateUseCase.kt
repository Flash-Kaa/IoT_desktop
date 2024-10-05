package domain.usecases

import domain.entity.ActuatorState
import domain.interfaces.actuator.Actuator

class UpdateActuatorStateUseCase(
    private val actuator: Actuator
) {
    operator fun invoke(value: ActuatorState) {
        actuator.actuatorState = value
    }
}