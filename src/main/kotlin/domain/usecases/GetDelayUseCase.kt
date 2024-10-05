package domain.usecases

import domain.interfaces.DelayRepository
import domain.interfaces.actuator.Actuator

class GetDelayUseCase(
    private val delay: DelayRepository
) {
    operator fun invoke() = delay.getDelay()
}