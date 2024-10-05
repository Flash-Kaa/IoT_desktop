package domain.interfaces.actuator

import domain.entity.ActuatorState
import domain.interfaces.Place

/**
 * Actuator for changing place values
 */
abstract class Actuator(
    place: Place
) {
    abstract var actuatorState: ActuatorState

    abstract var power: Float
}