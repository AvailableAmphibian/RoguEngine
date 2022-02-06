package util.helper

import map.Room
import map.tiles.Tile
import map.tiles.Wall
import util.displayer.BaseDisplayer

/**
 * Helper class used to have a matrix of tiles.
 *
 * Delegated the useful methods that we need from the first array.
 * Also add some methods useful to generate a cool map.
 */
class TileMatrix(val length:Int,  val width:Int) {
    fun outOfMap(x:Int, y:Int):Boolean = x < 0 || y < 0 || x >= length || y >= width

    private val matrix:Array<Array<Tile>> = Array(length) { Array(width){ Wall() } }

    operator fun get(index: Int) = matrix[index]
    operator fun iterator() = matrix.iterator()

    fun addRooms() {
        val r1 = Room(7, 15, 30, 40)
        val r2 = Room(0, 10, 10, 20)
        val r3 = Room(24, 30, 30, 40)
        val r4 = Room(10, 15, 50, 60)

        applyRoom(r1)
        applyRoom(r2)
        applyRoom(r3)
        applyRoom(r4)

        val displayer = BaseDisplayer()
        displayer.showMap(this)

        createCorridor(r1, r2)
        displayer.showMap(this)
        createCorridor(r3, r2)
        displayer.showMap(this)
        createCorridor(r4, r2)
        displayer.showMap(this)
        createCorridor(r3, r4)
        displayer.showMap(this)
        createCorridor(r4, r1)
        displayer.showMap(this)
    }

    fun applyRoom(room:Room) {
        applyRoomToMap(room)
        applyRoomWeight(room)
    }

    fun applyRoomToMap(room: Room) = (room.x1+1 until room.x2).iterator()
        .forEach { x ->
            (room.y1 +1 until room.y2).iterator().forEachRemaining { y -> matrix[x][y] = Tile(null) }
        }

    fun applyRoomWeight(room: Room) {
        val x1 = room.x1
        val y1 = room.y1
        val x2 = room.x2
        val y2 = room.y2

        matrix[x1][y1].weight = Tile.NO_WEIGHT
        matrix[x1][y1+1].weight = Tile.NO_WEIGHT
        matrix[x1+1][y1].weight = Tile.NO_WEIGHT

        matrix[x1][y2].weight = Tile.NO_WEIGHT
        matrix[x1][y2-1].weight = Tile.NO_WEIGHT
        matrix[x1+1][y2].weight = Tile.NO_WEIGHT

        matrix[x2][y1].weight = Tile.NO_WEIGHT
        matrix[x2][y1+1].weight = Tile.NO_WEIGHT
        matrix[x2-1][y1].weight = Tile.NO_WEIGHT

        matrix[x2][y2].weight = Tile.NO_WEIGHT
        matrix[x1][y2-1].weight = Tile.NO_WEIGHT
        matrix[x2-1][y1].weight = Tile.NO_WEIGHT

        (x1+1 until x2).forEach { x ->
            matrix[x][y1].weight = Tile.SMALL_WEIGHT
            matrix[x][y2].weight = Tile.SMALL_WEIGHT
        }

        (y1+1 until y2).forEach { y ->
            matrix[x1][y].weight = Tile.SMALL_WEIGHT
            matrix[x2][y].weight = Tile.SMALL_WEIGHT
        }
    }

    fun createCorridor(r1:Room, r2:Room) {
        val shortestPath = getShortestPathBetweenRooms(r1, r2)
        drawPath(shortestPath)
        applyWeightsOfPath(shortestPath)
    }

    fun getShortestPathBetweenRooms(r1:Room, r2:Room) : List<Pair<Int, Int>> {
        
        return listOf()
    }

    private fun drawPath(points: List<Pair<Int, Int>>) = points.forEach { (x, y) -> matrix[x][y] = Tile(null) }
    private fun applyWeightsOfPath(points: List<Pair<Int, Int>>) {
        points.forEach { (x,y) ->
            matrix[x][y].weight = Tile.SMALL_WEIGHT
            if (matrix[x+1][y] is Wall) matrix[x+1][y].weight = Tile.NO_WEIGHT
            if (matrix[x-1][y] is Wall) matrix[x-1][y].weight = Tile.NO_WEIGHT
            if (matrix[x][y+1] is Wall) matrix[x][y+1].weight = Tile.NO_WEIGHT
            if (matrix[x][y-1] is Wall) matrix[x][y-1].weight = Tile.NO_WEIGHT
        }
    }

    fun authorizePosition(xy: Pair<Int, Int>): Boolean {
        return false
    }
}
