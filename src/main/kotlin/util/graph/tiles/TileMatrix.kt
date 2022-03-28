package util.graph.tiles

import entities.Entity
import map.Room
import util.graph.SearchGraph
import util.helper.createPairOfPoints

/**
 * The Matrix used for the game in which units will move.
 * Extends the SearchGraph to make it work with LPA*.
 */
class TileMatrix(
    length:Int = 20,
    width:Int = 20):
    SearchGraph<Tile>(length, width) {
    override fun createDefaultMatrix(): Array<Array<Tile>> = Array(length) { Array(width) { Wall() } }

    override fun initMap() {
    }

    fun applyRoomToMap(room: Room) = (room.x1+1 until room.x2).iterator()
        .forEach { x ->
            (room.y1 +1 until room.y2).iterator().forEachRemaining { y -> matrix[x][y] = Tile().apply { xy = Pair(x,y) } } // TODO : Update to fit the construction
        }

    override fun applyPath(shortestPath: List<Pair<Int, Int>>) {
        shortestPath.iterator().forEach {
            this[it.first][it.second] = Tile().apply { xy = it }
        }
    }

    fun getRandomEmptyPosition(): Pair<Int, Int> {
        val pos = createPairOfPoints(length, width)
        if (this[pos] is Wall || this[pos].nEntState.value != null)
            return getRandomEmptyPosition()
        return pos
    }

    fun addEntity(entity: Entity<*>) {
        this[entity.position].nEntState.value = entity
    }

     fun withIndex(): Iterable<IndexedValue<Array<Tile>>> = matrix.withIndex()

}
