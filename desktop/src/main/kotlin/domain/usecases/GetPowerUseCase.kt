package domain.usecases

import domain.interfaces.actuator.Actuator

class GetPowerUseCase(
    private val actuator: Actuator
) {
    operator fun invoke() = actuator.power
}