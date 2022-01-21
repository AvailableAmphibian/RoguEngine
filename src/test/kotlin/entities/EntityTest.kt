package entities

import entities.monsters.Spider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import util.exception.CantMoveOnAnotherEntityException
import util.exception.CantMoveToTheSamePositionException
import util.helper.TileMatrix


abstract class EntityTest {
    lateinit var entity:Entity<*>
    val matrix = TileMatrix(25,25)
    abstract fun createEntity(): Entity<*>

    @BeforeEach
    fun initEntity() {
        entity = createEntity()
    }


    @Test
    abstract fun moveEntity()

    @Test
    fun cantMoveToTheSamePosition() {
        val pos = entity.position
        assertThrows<CantMoveToTheSamePositionException> { entity.move(pos, matrix) }
    }

    @Test
    fun cantMoveToTakenPosition() {
        val (x,y) = entity.position
        val otherEntity = Spider(Pair(x+1, y))
        matrix[x+1][y].entity = otherEntity
        assertThrows<CantMoveOnAnotherEntityException> { entity.move(otherEntity.position, matrix) }
    }
}