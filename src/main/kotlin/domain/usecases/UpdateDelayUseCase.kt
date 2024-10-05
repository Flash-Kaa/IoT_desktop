package domain.usecases

import domain.interfaces.DelayRepository

class UpdateDelayUseCase(
    private val repository: DelayRepository
) {
    operator fun invoke(value: Long) {
        repository.delay = value
    }
}