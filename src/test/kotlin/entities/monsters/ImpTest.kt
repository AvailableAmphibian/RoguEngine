package entities.monsters

import io.mockk.spyk

class ImpTest:MonsterTest() {
    override fun createEntity() = spyk(Imp(Pair(10, 10)))
}