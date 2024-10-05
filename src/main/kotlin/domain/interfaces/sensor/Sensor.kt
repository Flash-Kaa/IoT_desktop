package domain.interfaces.sensor

import domain.interfaces.Place

/**
 * Checking place data
 */
abstract class Sensor(
    private val place: Place
) {
    fun getData() = place.temperature
}