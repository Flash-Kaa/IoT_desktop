package data

import domain.interfaces.DelayRepository

class DelayRepositoryImpl: DelayRepository {
    private var delay = 1000L

    override fun getDelay() = delay

    override fun updateDelay(value: Long) {
        delay = value
    }
}