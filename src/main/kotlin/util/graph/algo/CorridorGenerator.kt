package util.graph.algo

import map.Room
import util.graph.corridorgen.CorridorGenerationGraph
import util.graph.corridorgen.CorridorGenerationNode
import util.graph.tiles.TileMatrix

class CorridorGenerator: AStarSearch<CorridorGenerationNode, CorridorGenerationGraph>() {
    lateinit var tileMatrix: TileMatrix
    lateinit var rooms: List<Room>

    override fun initGraph() = CorridorGenerationGraph(tileMatrix.length, tileMatrix.width).apply {
        gameMap = tileMatrix
        rooms = this@CorridorGenerator.rooms
    }

    fun linkRooms() {
        val iterator = rooms.iterator()
        var first = iterator.next()
        var second: Room
        while(iterator.hasNext()) {
            second = first
            first = iterator.next()
            source = first.center
            end = second.center

            map.initMap(end)
            compute()
            map.applyPath(getShortestPath())
        }
    }


}