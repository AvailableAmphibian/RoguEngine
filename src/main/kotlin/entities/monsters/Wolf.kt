package entities.monsters

import entities.Monster
import util.graph.SearchGraph
import util.graph.tiles.Tile

// dString looks like this : 🐺
class Wolf(pos:Pair<Int,Int>): Monster(5, 2, 'W', pos, "\uD83D\uDC3A")
