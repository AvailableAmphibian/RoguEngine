package entities

import decorators.BaseDecorator
import map.tiles.Wall
import util.exception.CantMoveOnAnotherEntityException
import util.exception.CantMoveToTheSamePositionException
import util.helper.TileMatrix
import java.util.*
import kotlin.random.Random

abstract class Entity<T:BaseDecorator?>(
    val baseHp:Int,
    val baseAtk:Int,
    val charDisplay: Char,
    val emojiDisplay: String,
    var position:Pair<Int, Int>) {

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


    protected open fun canMove(x:Int, y:Int, map: TileMatrix) = (x in posX-1..posX+1 && y in posY-1..posY+1)
            && map[x][y] !is Wall
            && map[x][y].entity == null


    fun move(newPos:Pair<Int, Int>, map:TileMatrix) {
        if(position == newPos)
            throw CantMoveToTheSamePositionException(this, newPos)
        val (x, y) = newPos
        val tileContent = map[x][y].entity
        if (tileContent != null)
            throw CantMoveOnAnotherEntityException(this, tileContent)

        val (oldX, oldY) = position

        map[oldX][oldY].entity = null
        map[x][y].entity = this
        position = newPos
    }


    // Todo : Make abstract
    open fun takeTurn(map: TileMatrix){
        val rnd = Random.Default

        do {
            val row = rnd.nextInt(map.length)
            val col = rnd.nextInt(map.width)
            val pos = Pair(row, col)
            val canMove = canMove(pos, map)
            if (canMove)
                move(pos, map)
        }while (!canMove)

    }

}