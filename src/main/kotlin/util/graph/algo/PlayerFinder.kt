package util.graph.algo

import entities.Monster
import entities.Player
import map.Stage
import util.graph.tiles.Tile
import util.graph.tiles.TileMatrix
import util.graph.tiles.Wall
import util.helper.NodesGetter

/**
 * Used to look for the player. Can be improved to make LPA*.
 */
class PlayerFinder(private val stage: Stage, private val monster:Monster): AStarSearch<Tile, TileMatrix>() {
    override val nodesGetter: NodesGetter<Tile> = monster::getNodesAround

    override fun initGraph(): TileMatrix {
        val matrix:TileMatrix
        with(stage.map) {
            matrix = TileMatrix(this.length, this.width)

            for ((rowId, row) in stage.map.withIndex()) {
                for ((colId, cell) in row.withIndex()) {
                    if (cell is Wall) {
                        matrix[rowId][colId] = Wall().apply { this.xy = Pair(rowId, colId) }
                    } else {

                            matrix[rowId][colId] = Tile().apply {
                                this.xy = cell.xy
                                this.nEntState.value = cell.nEntState.value

                        }
                    }
                }
            }
        }

        return matrix
    }

    /**
     * Used to find the player when they are far away
     */
    fun lookForPlayer(): List<Pair<Int, Int>> {
        source = monster.position
        end = stage.player.position

        map.initMap(end)
        try {
            compute()
        } catch (e:NoSuchElementException){
            return emptyList()
        }
        return getShortestPath()
    }
}