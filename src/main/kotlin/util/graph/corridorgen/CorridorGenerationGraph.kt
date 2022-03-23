package util.graph.corridorgen

import map.Room
import util.graph.NodeType
import util.graph.SearchGraph
import util.graph.tiles.TileMatrix
import util.graph.tiles.Wall

class CorridorGenerationGraph : SearchGraph<CorridorGenerationNode>() {
    lateinit var gameMap: TileMatrix
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
                if (tileType != NodeType.ROOM_TILE) {
                    this.tileType = NodeType.CORRIDOR_TILE
                    this.addSomeWeight()
                }
            }
        }
        TODO("Not yet implemented")
    }

    private fun initWalls() {
        for (x in 0 until length) {
            this[x][0] = CorridorGenerationNode().apply {
                xy = Pair(x, 0)
                tileType = NodeType.MATRIX_SIDE_TILE
            }
            this[x][width - 1] = CorridorGenerationNode().apply {
                xy = Pair(x, width - 1)
                tileType = NodeType.MATRIX_SIDE_TILE
            }
        }
        for (y in 1 until width - 1) {
            this[0][y] = CorridorGenerationNode().apply {
                xy = Pair(0, y)
                tileType = NodeType.MATRIX_SIDE_TILE
            }
            this[length - 1][y] = CorridorGenerationNode().apply {
                xy = Pair(length - 1, y)
                tileType = NodeType.MATRIX_SIDE_TILE
            }
        }
    }

    private fun adaptToMatrix() = (1 until length - 1).forEach { x ->
        (1 until width - 1).forEach { y ->
            this[x][y] = CorridorGenerationNode(true).apply {
                xy = Pair(x, y)
                tileType = if (gameMap[x][y] is Wall) NodeType.EMPTY_TILE else NodeType.CORRIDOR_TILE
            }
        }
    }

    private fun adaptRooms() {
        rooms.forEach {
            val corners = listOf(
                Pair(it.x1, it.y1),
                Pair(it.x1, it.y1 + 1),
                Pair(it.x1 + 1, it.y1),
                Pair(it.x1, it.y2),
                Pair(it.x1 + 1, it.y2),
                Pair(it.x1, it.y2 - 1),
                Pair(it.x2, it.y1),
                Pair(it.x2, it.y1 + 1),
                Pair(it.x2 - 1, it.y1),
                Pair(it.x2, it.y2),
                Pair(it.x2 - 1, it.y2),
                Pair(it.x2, it.y2 - 1)
            )
            (it.x1..it.x2).forEach { x ->
                (it.y1..it.y2).forEach { y ->
                    run {
                        val pos = Pair(x, y)
                        val onBorder = it.onBorder(pos)
                        if (onBorder) {
                            val inCorners = pos in corners
                            this[x][y] = CorridorGenerationNode(!inCorners).apply {
                                xy = pos
                                tileType = if (inCorners) NodeType.ROOM_CORNER_TILE else NodeType.ROOM_ENTRANCE_TILE
                                if (!inCorners) this.addMoreWeight()
                            }
                        } else this[x][y] = CorridorGenerationNode(true).apply {
                            xy = pos
                            tileType = NodeType.ROOM_TILE
                        }
                    }

                }
            }
        }
    }

    override fun applyPath(shortestPath: List<Pair<Int, Int>>) {
        shortestPath.iterator().forEach {
            if (rooms.none { room -> room.onBorder(it) || room.containsPosition(it) })
                matrix[it.first][it.second].tileType = NodeType.CORRIDOR_TILE
        }
        gameMap.applyPath(shortestPath)
    }
}
