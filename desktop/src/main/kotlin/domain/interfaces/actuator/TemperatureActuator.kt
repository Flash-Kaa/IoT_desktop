package domain.interfaces.actuator

import domain.entity.TemperatureActuatorState
import domain.interfaces.Place

/**
 * Actuator for changing temperature value
 */
abstract class TemperatureActuator(
    place: Place
) : Actuator(place) {
    abstract var temperatureChangerState: TemperatureActuatorState
}