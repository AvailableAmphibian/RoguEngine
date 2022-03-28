package entities

import decorators.BaseDecorator
import util.exception.CantMoveOnAnotherEntityException
import util.exception.CantMoveToTheSamePositionException
import util.graph.tiles.TileMatrix
import util.graph.tiles.Wall
import java.util.*
import kotlin.random.Random

abstract class Entity<T:BaseDecorator?>(
    val baseHp:Int,
    val baseAtk:Int,
    val charDisplay: Char,
    var position:Pair<Int, Int>,
    val emojiDisplay:String) {

    var currentHp = baseHp
    val decorators: ArrayList<T> = ArrayList<T>()
    val id:Int = idCounter++

    private val posX get() = position.first
    private val posY get() = position.second

    companion object {
        private var idCounter = 0
    }

    override fun toString(): String {
        return "${super.toString()} : $id, $position"
    }

    fun canMove(newPos: Pair<Int, Int>, map: TileMatrix) = canMove(newPos.first, newPos.second, map)

    /**
     * Verify if a unit can mote to another tile.
     * Should be overridden if the unit has a different moving pattern.
     */
    protected open fun canMove(x:Int, y:Int, map: TileMatrix) = (x in posX-1..posX+1 && y in posY-1..posY+1)
            && map[x][y] !is Wall
            && map[x][y].nEntState.value == null

    /**
     * Moves a unit.
     */
    fun move(newPos:Pair<Int, Int>, map:TileMatrix) {
        if(position == newPos)
            throw CantMoveToTheSamePositionException(this, newPos)
        val (x, y) = newPos
        val tileContent = map[x][y].nEntState.value
        if (tileContent != null)
            throw CantMoveOnAnotherEntityException(this, tileContent)

        val (oldX, oldY) = position

        map[oldX][oldY].nEntState.value = null
        map[x][y].nEntState.value = this
        position = newPos
    }

    abstract fun takeTurn(map: TileMatrix):Boolean

    fun attack(entity:Entity<*>) {
        entity.currentHp -= this.baseAtk
    }

    open fun die(map:TileMatrix) {
        map[position].nEntState.value = null
    }
}