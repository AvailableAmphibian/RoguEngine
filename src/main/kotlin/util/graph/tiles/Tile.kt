package util.graph.tiles

import entities.Entity
import util.graph.SearchNode

open class Tile: SearchNode() {
    var entity: Entity<*>? = null
}