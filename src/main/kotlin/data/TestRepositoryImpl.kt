package data

import domain.TestRepository

class TestRepositoryImpl: TestRepository {
    override fun getData(): Int {
        return 1
    }
}