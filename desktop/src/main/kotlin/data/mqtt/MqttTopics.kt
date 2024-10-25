package data.mqtt

enum class MqttTopics(val value: String) {
    TemperatureInsideTopic("sensor/temperature_inside"),
    TemperatureOutsideTopic("sensor/temperature_outside"),
    ActuatorModeTopic("actuator/mode"),
    ActuatorCommandTopic("actuator/command");

    companion object {
        // Deserialize by name
        fun deserialize(str: String?): MqttTopics? {
            entries.forEach {
                if (it.value == str) return it
            }

            return null
        }
    }
}