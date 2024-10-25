package domain.usecases

import domain.interfaces.actuator.TemperatureActuator

class GetTemperatureActuatorStateUseCase(
    private val temperatureActuator: TemperatureActuator
) {
    operator fun invoke() = temperatureActuator.temperatureChangerState
}