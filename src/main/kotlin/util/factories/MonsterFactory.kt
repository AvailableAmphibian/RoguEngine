package util.factories

import entities.Monster
import util.graph.tiles.TileMatrix

/**
 * Used to create monsters, implementation should be added to the Spawners.
 */
abstract class MonsterFactory{
    abstract fun createMonster(map: TileMatrix): Monster
}