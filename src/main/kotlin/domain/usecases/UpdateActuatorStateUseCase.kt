package domain.usecases

class UpdateActuatorStateUseCase(
    private val actuator: Any
) {
    operator fun invoke(value: Boolean) {}
}