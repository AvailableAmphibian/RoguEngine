package util.factories.monster

import entities.Monster
import entities.monsters.Spider
import util.factories.MonsterFactory
import util.graph.tiles.TileMatrix

class SpiderFactory: MonsterFactory() {
    override fun createMonster(map: TileMatrix) = Spider(map.getRandomEmptyPosition())
}