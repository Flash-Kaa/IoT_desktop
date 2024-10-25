package data

import domain.entity.ActuatorState
import domain.entity.TemperatureActuatorState
import domain.interfaces.Place
import domain.interfaces.actuator.Actuator
import domain.interfaces.actuator.TemperatureActuator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Actuator for room
 */
class InsideActuator(
    private val inside: Place
): TemperatureActuator(inside) {
    // It turns on when crossing the border
    private val minValue = 18f
    private val maxValue = 25f

    // How much does the temperature change
    override var power: Float = .1f

    // States
    override var actuatorState: ActuatorState = ActuatorState.Auto
    override var temperatureChangerState: TemperatureActuatorState = TemperatureActuatorState.None

    init {
        // Running a coroutine to checking the states
        CoroutineScope(SupervisorJob()).launch {
            while (true) {
                // Change states if Auto
                if (actuatorState == ActuatorState.Auto) {
                    val insideTemp = inside.temperature
                    temperatureChangerState = if (insideTemp < minValue) {
                        TemperatureActuatorState.Hot
                    } else if (insideTemp > maxValue) {
                        TemperatureActuatorState.Cold
                    } else {
                        TemperatureActuatorState.None
                    }
                }

                // Changing temperature for 0.1 sec
                if (temperatureChangerState == TemperatureActuatorState.Cold) {
                    inside.temperature -= power / 10
                } else if (temperatureChangerState == TemperatureActuatorState.Hot) {
                    inside.temperature += power / 10
                }

                // Sleep coroutine 0.1 sec
                delay(100L)
            }
        }
    }
}