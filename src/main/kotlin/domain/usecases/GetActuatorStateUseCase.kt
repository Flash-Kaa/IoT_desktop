package domain.usecases

import domain.interfaces.actuator.Actuator

class GetActuatorStateUseCase(
    private val actuator: Actuator
) {
    operator fun invoke() = actuator.actuatorState
}