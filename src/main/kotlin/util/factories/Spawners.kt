package util.factories

import util.graph.tiles.TileMatrix

/**
 * Wraps the different MonsterFactories to make them accessible more easily.
 */
class Spawners: ArrayList<MonsterFactory>() {
    fun spawnFromAny(map:TileMatrix) = this.random().createMonster(map)
}