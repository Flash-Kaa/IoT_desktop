package domain.usecases

import domain.interfaces.sensor.Sensor

class UpdateTemperatureUseCase(
    private val sensor: Sensor
) {
    operator fun invoke(value: Float) = sensor.updateData(value)
}