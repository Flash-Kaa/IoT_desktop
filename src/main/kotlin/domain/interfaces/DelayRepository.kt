package domain.interfaces

interface DelayRepository {
    fun getDelay(): Long

    fun updateDelay(value: Long)
}