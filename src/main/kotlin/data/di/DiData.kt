package data.di

import data.DelayRepositoryImpl
import data.InsideActuator
import data.Room
import data.Street
import data.TemperatureSensor
import domain.interfaces.DelayRepository
import domain.interfaces.actuator.Actuator
import domain.interfaces.sensor.Sensor

object DiData {
    private val street: Street by lazy { Street() }
    private val room : Room by lazy { Room(street) }

    val insideSensor: Sensor by lazy { TemperatureSensor(room) }
    val outsideTemperature: Sensor by lazy { TemperatureSensor(street) }
    val actuator: Actuator by lazy { InsideActuator(room) }

    val delayRepository: DelayRepository by lazy { DelayRepositoryImpl() }
}