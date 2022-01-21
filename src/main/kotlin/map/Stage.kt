package map

import util.TileMatrix
import entities.Player
import entities.monsters.Spider
import map.tiles.Stairs

class Stage(/*val rooms: List<Room>, val roomLinks:Map<Int, List<Int>>*/) {
    val map = TileMatrix(7, 7)
    lateinit var player:Player

    fun spawnPlayer() {
        player = Player(Pair(4,4))
        map[4][4].entity = player
        for (line in map)
            for (tile in line)
                tile.discovered = true
//        for(i in 2..5){
//            for(j in 2 .. 5) {
//                map[i][j].discovered = true
//            }
//        }
    }

    fun movePlayer() {
        val (xOld, yOld) = player.position
        val (xMove, yMove) = player.move(map)
        val x = xOld + xMove
        val y = yOld + yMove

        if (map.outOfMap(x, y))
            return

        if (map[x][y].isFree()) {
            map[x][y].entity = player
            map[xOld][yOld].entity = null
        }
    }

    fun createStairs(i:Int, j:Int) {
        map[i][j] = Stairs()
    }
}