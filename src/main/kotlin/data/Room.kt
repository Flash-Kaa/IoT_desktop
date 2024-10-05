package data

import domain.interfaces.Place
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Room place
 */
class Room(
    private val street: Street
): Place(17f) {
    // Coefficient for achieving thermal balance
    private val alpha = 0.001f

    init {
        // Running coroutine for updating value for achieving thermal balance
        CoroutineScope(SupervisorJob()).launch {
            while (true) {
                // Change actual temperature
                temperature += alpha * (street.temperature - temperature)

                // Sleep coroutine 1 sec
                delay(1000)
            }
        }
    }
}