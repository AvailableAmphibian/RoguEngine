package entities

import entities.monsters.Spider
import io.mockk.impl.annotations.MockK
import map.Room
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import util.exception.CantMoveOnAnotherEntityException
import util.exception.CantMoveToTheSamePositionException
import util.graph.tiles.TileMatrix
import kotlin.test.assertEquals
import kotlin.test.assertNull


abstract class EntityTest {
    @MockK
    lateinit var entity:Entity<*>
    lateinit var matrix:TileMatrix
    abstract fun createEntity(): Entity<*>

    @BeforeEach
    fun initData() {
        entity = createEntity()
        matrix = TileMatrix(25,25).apply { applyRoomToMap(Room(5, 15, 5, 15)) }
    }

    @Test
    fun moveEntity() {
        val pos = Pair(9, 11)
        entity.move(pos, matrix)
        val (x,y) = entity.position

        assertEquals(9, x, "X has changed")
        assertEquals(11, y, "Y has changed")

//        assertEquals(entity, matrix[9][11].entity, "Entity is at the new position")
        assertEquals(entity, matrix[9][11].nEntState.value, "Entity is at the new position")
//        assertNull(matrix[10][10].entity, "Entity has successfully moved")
        assertNull(matrix[10][10].nEntState.value, "Entity has successfully moved")
    }

    @Test
    fun cantMoveToTheSamePosition() {
        val pos = entity.position
        assertThrows<CantMoveToTheSamePositionException> { entity.move(pos, matrix) }
    }

    @Test
    fun cantMoveToTakenPosition() {
        val (x,y) = entity.position
        val otherEntity = Spider(Pair(x+1, y))
//        matrix[x+1][y].entity = otherEntity
        matrix[x+1][y].nEntState.value = otherEntity
        assertThrows<CantMoveOnAnotherEntityException> { entity.move(otherEntity.position, matrix) }
    }
}
