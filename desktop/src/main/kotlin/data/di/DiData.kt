package data.di

import data.DelayRepositoryImpl
import data.InsideActuator
import data.Room
import data.Street
import data.TemperatureSensor
import domain.interfaces.DelayRepository
import domain.interfaces.actuator.TemperatureActuator
import domain.interfaces.sensor.Sensor

/**
 * DI substitute
 */
object DiData {
    // places
    val street: Street by lazy { Street() }
    val room: Room by lazy { Room(street) }

    // Sensors
    val insideSensor: Sensor by lazy { TemperatureSensor(room) }
    val outsideTemperature: Sensor by lazy { TemperatureSensor(street) }

    // Actuators
    val actuator: TemperatureActuator by lazy { InsideActuator(room) }

    // Other repositories
    val delayRepository: DelayRepository by lazy { DelayRepositoryImpl() }
}