package data

import domain.entity.ActuatorState
import domain.interfaces.actuator.Actuator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InsideActuator(
    private val inside: Place
): Actuator(inside) {
    private val minValue = 10f
    private val maxValue = 30f

    private val _flow = MutableStateFlow<ActuatorState>(ActuatorState.Auto)
    override val actuatorOn: Flow<ActuatorState> = _flow.asStateFlow()

    private var power = .1f

    override fun getPower() = power

    override fun updatePower(value: Float) {
        power = value
    }

    init {
        CoroutineScope(SupervisorJob()).launch {
            while (true) {

                val insideTemp = inside.getTemperature()
                if (insideTemp < minValue) {

                } else if (insideTemp > maxValue) {

                }

                delay(100L)
            }
        }
    }
}