package util.factories.monster

import entities.Monster
import entities.monsters.Wolf
import util.factories.MonsterFactory
import util.graph.tiles.TileMatrix

class WolfFactory: MonsterFactory() {
    override fun createMonster(map: TileMatrix) = Wolf(map.getRandomEmptyPosition())
}