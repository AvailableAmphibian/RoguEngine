package entities.monsters

import io.mockk.spyk

class SpiderTest:MonsterTest() {
    override fun createEntity() = spyk(Spider(Pair(10, 10)))
}
