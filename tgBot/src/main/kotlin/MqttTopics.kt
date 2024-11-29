enum class MqttTopics(val value: String) {
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