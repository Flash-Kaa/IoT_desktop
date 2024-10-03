package domain.usecases

class UpdateOutsideTempUseCase(
    private val detector: Any
) {
    operator fun invoke(value: Float) {}
}