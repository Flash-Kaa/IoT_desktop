package domain.usecases

import domain.interfaces.sensor.Sensor

class GetTemperatureUseCase(
    private val sensor: Sensor
) {
    operator fun invoke() = sensor.getData()
}