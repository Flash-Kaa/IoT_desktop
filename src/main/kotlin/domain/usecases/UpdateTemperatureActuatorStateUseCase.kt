package domain.usecases

import domain.entity.TemperatureActuatorState
import domain.interfaces.actuator.TemperatureActuator

class UpdateTemperatureActuatorStateUseCase(
    private val temperatureActuator: TemperatureActuator
) {
    operator fun invoke(value: TemperatureActuatorState) {
        temperatureActuator.temperatureChangerState = value
    }
}