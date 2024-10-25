package data

import domain.interfaces.DelayRepository

/**
 * data storage for delay value
 */
class DelayRepositoryImpl: DelayRepository {
    override var delay = 1000L
}