package util.graph.architest.algo

import map.Room
import util.graph.architest.corridorgen.CorridorGenerationGraph
import util.graph.architest.corridorgen.CorridorGenerationNode
import util.graph.architest.tiles.TM

class CorridorGenerator: AStarSearch<CorridorGenerationNode, CorridorGenerationGraph>() {
    lateinit var tileMatrix:TM
    lateinit var rooms: List<Room>

    override fun initGraph() = CorridorGenerationGraph().apply { gameMap = tileMatrix }

    fun linkRooms() {
        val iterator = rooms.iterator()
        var first = iterator.next()
        var second: Room
        while(iterator.hasNext()) {
            second = first
            first = iterator.next()
            source = first.center
            end = second.center

            compute()
            tileMatrix.applyPath(getShortestPath())
        }
        TODO("Not yet implemented")
    }


}