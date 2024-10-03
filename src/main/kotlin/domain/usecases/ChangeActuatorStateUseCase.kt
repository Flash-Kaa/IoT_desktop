package domain.usecases

class ChangeActuatorStateUseCase(
    private val actuator: Any
) {
    operator fun invoke(value: Boolean) {}
}