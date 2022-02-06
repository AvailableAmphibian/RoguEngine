package util.graph

import map.Room
import map.tiles.Tile
import map.tiles.Wall
import util.search.model.AStarNode
import util.search.model.NodeType
import util.search.model.SearchGraph

class CorridorGenerationGraph(private val matrix: TileMatrix, private val rooms: List<Room>): SearchGraph<Any?>(matrix.length, matrix.width) {
    override fun initMap() {
        initWalls()
        adaptToMatrix()
        adaptRooms()
    }

    override fun updateMap() {
//        adaptCorridors() // ?
    }

    private fun initWalls() {
        for (x in 0 until length) {
            map[x][0] = AStarNode.createUnauthorizedNode(Pair(x, 0)).apply { tileType = NodeType.MATRIX_SIDE_TILE }
            map[x][width-1] = AStarNode.createUnauthorizedNode(Pair(x, width - 1))
                .apply { tileType = NodeType.MATRIX_SIDE_TILE }
        }
        for (y in 1 until width - 1) {
            map[0][y] = AStarNode.createUnauthorizedNode(Pair(0, y)).apply { tileType = NodeType.MATRIX_SIDE_TILE }
            map[length - 1][y] = AStarNode.createUnauthorizedNode(Pair(length - 1, y))
                .apply { tileType = NodeType.MATRIX_SIDE_TILE }
        }
    }

    private fun adaptToMatrix() = (1 until length - 1).forEach { x ->
        (1 until width-1).forEach { y ->
            map[x][y] = AStarNode(Tile.MIN_WEIGHT, true, Pair(x,y)).apply {
                tileType = if (matrix[x][y] is Wall) NodeType.EMPTY_TILE else NodeType.ROOM_TILE
            }
        }
    }

    private fun adaptRooms() {
        rooms.forEach {
            (it.x1 .. it.x2).forEach { x ->
                if (x !in it.x1+2 until  it.x2-1) {
                    map[x][it.y1] = AStarNode(Tile.NO_WEIGHT, false, Pair(x,it.y1)).apply { tileType =
                        NodeType.ROOM_CORNER_TILE
                    }
                    map[x][it.y2] = AStarNode(Tile.NO_WEIGHT, false, Pair(x,it.y2)).apply { tileType =
                        NodeType.ROOM_CORNER_TILE
                    }
                }
            }
            (it.y1+1 until it.y2).forEach { y ->
                if (y !in it.y1+2 until  it.y2-1) {
                    map[it.x1][y] = AStarNode(Tile.NO_WEIGHT, false, Pair(it.x1, y)).apply { tileType =
                        NodeType.ROOM_CORNER_TILE
                    }
                    map[it.x2][y] = AStarNode(Tile.NO_WEIGHT, false, Pair(it.x2,y)).apply { tileType =
                        NodeType.ROOM_CORNER_TILE
                    }
                }
            }
        }
    }


}