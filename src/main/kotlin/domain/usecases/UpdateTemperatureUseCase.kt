package domain.usecases

import domain.interfaces.Place

class UpdateTemperatureUseCase(
    private val place: Place
) {
    operator fun invoke(value: Float) {
        place.temperature = value    }
}