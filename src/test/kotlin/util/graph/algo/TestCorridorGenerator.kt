package util.graph.algo

import map.Room
import org.junit.jupiter.api.Test
import util.graph.NodeType
import util.graph.corridorgen.CorridorGenerationGraph
import util.graph.corridorgen.CorridorGenerationNode
import util.graph.tiles.TileMatrix
import kotlin.test.assertEquals

class TestCorridorGenerator : TestAStarSearch<CorridorGenerationNode, CorridorGenerationGraph>() {
    override fun createSearcher(): AStarSearch<CorridorGenerationNode,CorridorGenerationGraph> {
        return CorridorGenerator().apply {
            tileMatrix = TileMatrix()
            rooms = emptyList()
            initGraph()
        }
    }

    override fun setAWall() {
        for (i in 2 until 5) {
                searcher.map[i][2] = CorridorGenerationNode()
        }
    }

    @Test
    fun `generating a straight corridor between source and a room`() {
        with(searcher as CorridorGenerator) {
            val r1 = Room(1,5,6, 10)
            source = Pair(3, 2)
            rooms += r1
            end = r1.center
            map.rooms += r1
            map.initMap(end)

            compute()
            map.applyPath(getShortestPath())

            assertEquals(NodeType.CORRIDOR_TILE, map[3][2].tileType) // Source
            assertEquals(NodeType.CORRIDOR_TILE, map[3][3].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[3][4].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[3][5].tileType)
            assertEquals(NodeType.ROOM_ENTRANCE_TILE, map[3][6].tileType) // Room Entrance
            assertEquals(NodeType.ROOM_TILE, map[3][7].tileType)
            assertEquals(NodeType.ROOM_TILE, map[3][8].tileType) // End
        }
    }

    /**
     * The path should look like this :
     *
     * #############
     * #S..#########
     * ###...#######
     * #####.#XXXXX#
     * #####.#X...X#
     * #####....E.X#
     * #######X...X#
     * #######XXXXX#
     * #############
     */
    @Test
    fun `generating an angled corridor between source and a room`() {
        with(searcher as CorridorGenerator) {
            val room = Room(10, 14, 6, 10)
            source = Pair(8, 1)
            end = room.center
            rooms += room
            map.rooms += room
            map.initMap(end)

            compute()
            map.applyPath(getShortestPath())

            assertEquals(NodeType.CORRIDOR_TILE, map[8][1].tileType) // Source
            assertEquals(NodeType.CORRIDOR_TILE, map[8][2].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[8][3].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[9][3].tileType)
            assertEquals(NodeType.CORRIDOR_TILE,map[10][3].tileType)
            assertEquals(NodeType.CORRIDOR_TILE,map[10][4].tileType)
            assertEquals(NodeType.CORRIDOR_TILE,map[11][4].tileType)
            assertEquals(NodeType.CORRIDOR_TILE,map[11][5].tileType)
            assertEquals(NodeType.CORRIDOR_TILE,map[12][5].tileType)
            assertEquals(NodeType.ROOM_ENTRANCE_TILE, map[12][6].tileType) // Room entrance
            assertEquals(NodeType.ROOM_TILE, map[12][7].tileType)
            assertEquals(NodeType.ROOM_TILE, map[12][8].tileType) // End
        }
    }

    @Test
    fun `not crossing a room`() {
        with(searcher as CorridorGenerator){
            val r1 = Room(0,5,0,5)
            val r2 = Room(0,5,7,12)
            val r3 = Room(0,5,14,19)

            source = r1.center
            end = r3.center
            rooms += r1
            rooms += r2
            rooms += r3
            map.rooms += r1
            map.rooms += r2
            map.rooms += r3
            map.initMap(end)
            compute()
            map.applyPath(getShortestPath())

            assertEquals(NodeType.ROOM_TILE, map[2][2].tileType) // Source
            assertEquals(NodeType.ROOM_TILE, map[2][3].tileType)
            assertEquals(NodeType.ROOM_TILE, map[2][4].tileType)
            assertEquals(NodeType.ROOM_ENTRANCE_TILE, map[2][5].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[2][6].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[3][6].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[4][6].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[5][6].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[6][6].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[6][7].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[6][8].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[6][9].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[6][10].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[6][11].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[6][12].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[6][13].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[5][13].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[4][13].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[3][13].tileType)
            assertEquals(NodeType.ROOM_ENTRANCE_TILE, map[3][14].tileType) // Room entrance
            assertEquals(NodeType.ROOM_TILE, map[3][15].tileType)
            assertEquals(NodeType.ROOM_TILE, map[3][16].tileType)
            assertEquals(NodeType.ROOM_TILE, map[2][16].tileType) // end
        }
    }

    @Test
    fun `room linking`() {
        with(searcher as CorridorGenerator) {
            val r1 = Room(0, 5, 0, 5)
            val r2 = Room(0, 5, 7, 12)
            val r3 = Room(0, 5, 14, 19)

            source = r1.center
            end = r3.center
            rooms += r1
            rooms += r2
            rooms += r3
            map.rooms += r1
            map.rooms += r2
            map.rooms += r3

            linkRooms()

            assertEquals(NodeType.CORRIDOR_TILE, map[2][6].tileType)
            assertEquals(NodeType.CORRIDOR_TILE, map[2][13].tileType)
        }
    }
}