package map.tiles

import decorators.BaseDecorator
import entities.Entity

open class Tile(var entity:Entity<*>?) {
    var discovered = false
    open val noEntityDisplay get() = "."
    fun displayContent():String = if (discovered) entity?.displayString ?: noEntityDisplay else " "

    open fun isFree() = entity == null
}