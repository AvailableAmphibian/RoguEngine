package util.graph.architest.tiles

import map.Room
import util.graph.architest.SearchGraph

class TM(
    length:Int = 20,
    width:Int = 20):
    SearchGraph<Tile>(length, width) {
    override fun createDefaultMatrix(): Array<Array<Tile>> = Array(length) { Array(width) { Wall() } }

    override fun initMap() {

        TODO("Not yet implemented")
    }

    override fun updateMap(listPos: List<Pair<Int, Int>>) {
        TODO("Not yet implemented")
    }

    fun applyRoomToMap(room: Room) = (room.x1+1 until room.x2).iterator()
        .forEach { x ->
            (room.y1 +1 until room.y2).iterator().forEachRemaining { y -> matrix[x][y] = Tile() } // TODO : Update to fit the construction
        }

    fun applyPath(shortestPath: List<Pair<Int, Int>>) {
        shortestPath.iterator().forEach {
            this[it.first][it.second] = Tile()
        }
        TODO("Update to fit the construction")
    }

}