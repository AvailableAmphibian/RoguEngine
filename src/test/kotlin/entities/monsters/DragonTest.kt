package entities.monsters

import io.mockk.spyk

class DragonTest :MonsterTest() {
    override fun createEntity() = spyk(Dragon(Pair(10, 10)))
}