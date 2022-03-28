package entities.monsters

import entities.Monster
import util.graph.SearchGraph
import util.graph.tiles.Tile

// dString looks like this : 👿
class Imp(pos:Pair<Int,Int>): Monster(3, 1, 'I', pos, "\uD83D\uDC7F")