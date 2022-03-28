package entities.monsters

import entities.Monster
import util.graph.SearchGraph
import util.graph.tiles.Tile

// dString looks like this : 🕷️
class Spider(pos: Pair<Int, Int>) : Monster(1, 1, 'S', pos, "\uD83D\uDD77️") {
    override fun getNodesAround(xy: Pair<Int, Int>, map: SearchGraph<Tile>): List<Tile> {
        val cells = mutableListOf<Tile>()
        val (x, y) = xy
        val xm1 = x - 1 > 0
        val xp1 = x + 1 < map.length - 1
        val ym1 = y - 1 > 0
        val yp1 = y + 1 < map.width - 1

        if (xm1 && map[x - 1][y].authorized) {
            cells += map[x - 1][y]
        }
        if (xp1 && map[x + 1][y].authorized) {
            cells += map[x + 1][y]
        }
        if (ym1 && map[x][y - 1].authorized) {
            cells += map[x][y - 1]
        }
        if (yp1 && map[x][y + 1].authorized) {
            cells += map[x][y + 1]
        }

        if (xm1 && ym1 && map[x - 1][y - 1].authorized)
            cells += map[x - 1][y - 1]
        if (xm1 && yp1 && map[x - 1][y + 1].authorized)
            cells += map[x - 1][y + 1]
        if (xp1 && ym1 && map[x + 1][y - 1].authorized)
            cells += map[x + 1][y - 1]
        if (xp1 && yp1 && map[x + 1][y + 1].authorized)
            cells += map[x + 1][y + 1]


        return cells
    }
}
