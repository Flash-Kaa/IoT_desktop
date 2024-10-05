package domain.usecases

import domain.interfaces.DelayRepository

class GetDelayUseCase(
    private val repository: DelayRepository
) {
    operator fun invoke() = repository.delay
}