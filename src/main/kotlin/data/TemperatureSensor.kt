package data

import domain.interfaces.sensor.Sensor

class TemperatureSensor(
    place: Place
): Sensor(place)