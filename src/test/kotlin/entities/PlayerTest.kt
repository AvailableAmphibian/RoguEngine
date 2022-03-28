package entities

import io.mockk.spyk
import org.junit.jupiter.api.Test
import util.player_action.PlayerActDown
import util.player_action.PlayerActLeft
import util.player_action.PlayerActRight
import util.player_action.PlayerActUp
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PlayerTest: EntityTest() {
    override fun createEntity(): Entity<*> = spyk(Player(Pair(10, 10)))

    @Test
    fun testMoveUp() {
        val res1 = PlayerActUp.doIt(entity as Player, matrix)

        assertTrue(res1, "Player has moved")

        assertEquals(9, entity.position.first, "X has changed")
        assertEquals(10, entity.position.second, "Y has not changed")

        val res2 = PlayerActUp.doIt(entity as Player, matrix)

        assertTrue(res2, "Player has moved")

        assertEquals(8, entity.position.first, "X has changed")
        assertEquals(10, entity.position.second, "Y has not changed")
    }

    @Test
    fun testMoveLeft() {
        assertTrue(PlayerActLeft.doIt(entity as Player, matrix))

        assertEquals(10, entity.position.first, "X has not changed")
        assertEquals(9, entity.position.second, "Y has changed")

        assertTrue(PlayerActLeft.doIt(entity as Player, matrix))

        assertEquals(10, entity.position.first, "X has not changed")
        assertEquals(8, entity.position.second, "Y has changed")
    }

    @Test
    fun testMoveDown() {
        assertTrue(PlayerActDown.doIt(entity as Player, matrix))

        assertEquals(11, entity.position.first, "X has changed")
        assertEquals(10, entity.position.second, "Y has not changed")

        assertTrue(PlayerActDown.doIt(entity as Player, matrix))

        assertEquals(12, entity.position.first, "X has changed")
        assertEquals(10, entity.position.second, "Y has not changed")
    }

    @Test
    fun testMoveRight() {
        assertTrue(PlayerActRight.doIt(entity as Player, matrix))

        assertEquals(10, entity.position.first, "X has not changed")
        assertEquals(11, entity.position.second, "Y has changed")

        assertTrue(PlayerActRight.doIt(entity as Player, matrix))

        assertEquals(10, entity.position.first, "X has not changed")
        assertEquals(12, entity.position.second, "Y has changed")
    }
}
