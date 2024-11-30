package domain.entity

// Temperature direction
enum class TemperatureActuatorState {
    Hot, Cold, None;

    companion object {
        // Deserialize by name
        fun deserialize(str: String?): TemperatureActuatorState? {
            TemperatureActuatorState.entries.forEach {
                if (it.name == str?.dropWhile { !it.isLetter() }?.dropLastWhile { !it.isLetter() }) return it
            }

            return null
        }
    }
}