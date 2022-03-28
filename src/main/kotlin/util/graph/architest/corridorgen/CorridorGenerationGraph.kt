package util.graph.architest.corridorgen

import map.Room
import util.graph.architest.SearchGraph
import util.graph.architest.tiles.TM
import util.graph.architest.tiles.Wall
import util.search.model.NodeType

class CorridorGenerationGraph: SearchGraph<CorridorGenerationNode>() {
    lateinit var gameMap:TM
    lateinit var rooms: List<Room>
    override fun createDefaultMatrix(): Array<Array<CorridorGenerationNode>> {
        return Array(length) { Array(width) { CorridorGenerationNode(true) } }
    }

    override fun initMap() {
        initWalls()
        adaptToMatrix()
        adaptRooms()
    }

    override fun updateMap(listPos: List<Pair<Int, Int>>) {
        gameMap.updateMap(listPos)
        listPos.forEach {
            this[it.first][it.second] = CorridorGenerationNode(true).apply {
                this.xy = it
                if (tileType != NodeType.ROOM_TILE)
                    this.tileType = NodeType.CORRIDOR_TILE
            }
        }
        TODO("Not yet implemented")
    }

    private fun initWalls() {
        for (x in 0 until length) {
            this[x][0] = CorridorGenerationNode().apply {
                xy = Pair(x,0)
                tileType = NodeType.MATRIX_SIDE_TILE
            }
            this[x][width-1] = CorridorGenerationNode().apply {
                xy = Pair(x,width-1)
                tileType = NodeType.MATRIX_SIDE_TILE
            }
        }
        for (y in 1 until width - 1) {
            this[0][y] = CorridorGenerationNode().apply {
                xy = Pair(0,y)
                tileType = NodeType.MATRIX_SIDE_TILE
            }
            this[length - 1][y] = CorridorGenerationNode().apply {
                xy = Pair(length - 1, y)
                tileType = NodeType.MATRIX_SIDE_TILE
            }
        }
    }

    private fun adaptToMatrix() = (1 until length - 1).forEach { x ->
        (1 until width-1).forEach { y ->
            this[x][y] = CorridorGenerationNode(true).apply {
                xy = Pair(x,y)
                tileType = if (gameMap[x][y] is Wall) NodeType.EMPTY_TILE else NodeType.ROOM_TILE
            }
        }
    }

    private fun adaptRooms() {
        rooms.forEach {
            (it.x1 .. it.x2).forEach { x ->
                if (x !in it.x1+2 until  it.x2-1) {
                    this[x][it.y1] = CorridorGenerationNode().apply {
                        xy = Pair(x,it.y1)
                        tileType = NodeType.ROOM_CORNER_TILE
                    }
                    this[x][it.y2] = CorridorGenerationNode().apply {
                        xy = Pair(x,it.y2)
                        tileType = NodeType.ROOM_CORNER_TILE
                    }
                }
            }
            (it.y1+1 until it.y2).forEach { y ->
                if (y !in it.y1+2 until  it.y2-1) {
                    this[it.x1][y] =  CorridorGenerationNode().apply {
                        xy = Pair(it.x1,y)
                        tileType = NodeType.ROOM_CORNER_TILE
                    }
                    this[it.x2][y] =  CorridorGenerationNode().apply {
                        xy = Pair(it.x2, y)
                        tileType = NodeType.ROOM_CORNER_TILE
                    }
                }
            }
        }
    }
}
