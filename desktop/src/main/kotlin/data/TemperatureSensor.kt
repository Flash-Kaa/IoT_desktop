package data

import domain.interfaces.Place
import domain.interfaces.sensor.Sensor

/**
 * Sensor for checking temperature
 */
class TemperatureSensor(
    place: Place
): Sensor(place)