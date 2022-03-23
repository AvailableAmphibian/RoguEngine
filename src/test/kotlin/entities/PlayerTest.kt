package entities

import io.mockk.spyk
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PlayerTest: EntityTest() {
    override fun createEntity(): Entity<*> = spyk(Player(Pair(10, 10)))

    override fun moveEntity() {
        val pos = Pair(9, 11)
        entity.move(pos, matrix)
        val (x,y) = entity.position

        assertEquals(9, x, "X has changed")
        assertEquals(11, y, "Y has changed")

        assertEquals(entity, matrix[9][11].entity, "Entity is at the new position")
        assertNull(matrix[10][10].entity, "Entity has successfully moved")
    }
}