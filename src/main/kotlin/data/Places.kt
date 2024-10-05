package data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Street: Place(35f)

class Room(
    private val street: Street
): Place(17f) {
    private val alpha = 0.001f

    init {
        CoroutineScope(SupervisorJob()).launch {
            while (true) {
                var current = getTemperature()
                current += alpha * (street.getTemperature() - current)
                changeTemperature(current)

                delay(1000)
            }
        }
    }
}

abstract class Place(
    private var temperature: Float
) {
    fun getTemperature() = temperature

    fun changeTemperature(value: Float) {
        temperature = value
    }
}