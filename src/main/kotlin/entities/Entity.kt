package entities

import decorators.BaseDecorator
import util.exception.CantMoveOnAnotherEntityException
import util.exception.CantMoveToTheSamePositionException
import util.helper.TileMatrix
import java.util.*

abstract class Entity<T:BaseDecorator?>(
    val baseHp:Int,
    val baseAtk:Int,
    val charDisplay: Char,
    val emojiDisplay: String,
    var position:Pair<Int, Int>) {

    var currentHp = baseHp
    val decorators = ArrayList<T>()
    val id:Int = idCounter++

    companion object {
        private var idCounter = 0
    }

    override fun toString(): String {
        return "${super.toString()} : $id, $position"
    }

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
    open fun takeTurn(map: TileMatrix){}

}