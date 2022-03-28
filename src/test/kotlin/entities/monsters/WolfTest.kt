package entities.monsters

import io.mockk.spyk

class WolfTest:MonsterTest() {
    override fun createEntity() = spyk(Wolf(Pair(10, 10)))
}