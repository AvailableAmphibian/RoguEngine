package entities

import decorators.MonsterDecorator
import util.helper.StageHelper
import util.graph.SearchGraph
import util.graph.algo.PlayerFinder
import util.graph.tiles.Tile
import util.graph.tiles.TileMatrix

abstract class Monster(bHp:Int, bAtk:Int, dC:Char, pos:Pair<Int, Int>, dString: String): Entity<MonsterDecorator>(
    bHp,
    bAtk,
    dC,
    pos,
    dString
) {
//    val playerFinder = PlayerFinder(Stage(), this)
    override fun takeTurn(map: TileMatrix):Boolean {
        val stage = StageHelper.currentStage

        val nodesInRange = getNodesAround(position, stage.map)
        if (nodesInRange.any { it.xy == stage.player.position }) {
            attack(stage.player)
        } else if (nodesInRange.isNotEmpty()){
            val playerFinder = PlayerFinder(stage, this)
            val pathToPlayer = playerFinder.lookForPlayer()
            val goingTo = pathToPlayer.first { node -> nodesInRange.any { it.xy == node } }
            if (stage.map[goingTo].nEntState.value !is Monster)
                move(goingTo, stage.map)
        }

        return true
    }

    open fun getNodesAround(xy: Pair<Int, Int>, map: SearchGraph<Tile>): List<Tile> {
        val cells = mutableListOf<Tile>()
        val (x,y) = xy
        if (x-1 > 0 && map[x-1][y].authorized)
            cells += map[x-1][y]
        if (x+1 < map.length-1 && map[x+1][y].authorized)
            cells += map[x+1][y]
        if (y-1 > 0 && map[x][y-1].authorized)
            cells += map[x][y-1]
        if (y+1 < map.width-1 && map[x][y+1].authorized)
            cells += map[x][y+1]

        return cells
    }
}
