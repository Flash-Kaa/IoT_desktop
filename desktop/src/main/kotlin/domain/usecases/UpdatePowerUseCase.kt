package domain.usecases

import domain.interfaces.actuator.Actuator

class UpdatePowerUseCase(
    private val actuator: Actuator
) {
    operator fun invoke(value: Float) {
        actuator.power = value
    }
}