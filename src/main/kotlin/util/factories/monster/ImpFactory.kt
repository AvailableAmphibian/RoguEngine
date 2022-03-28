package util.factories.monster

import entities.Monster
import entities.monsters.Imp
import util.factories.MonsterFactory
import util.graph.tiles.TileMatrix

class ImpFactory: MonsterFactory() {
    override fun createMonster(map: TileMatrix) = Imp(map.getRandomEmptyPosition())
}