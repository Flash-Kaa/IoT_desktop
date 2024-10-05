package domain.interfaces.sensor

import data.Place

abstract class Sensor(
    private val place: Place
) {
    fun getData() = place.getTemperature()

    fun updateData(value: Float) = place.changeTemperature(value)
}