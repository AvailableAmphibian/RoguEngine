package map.tiles

import entities.Entity

open class Tile {
    companion object {
        const val MIN_WEIGHT = 1.0
        const val NO_WEIGHT = -1.0
        const val SMALL_WEIGHT = 1.5
    }

    var entity: Entity<*>? = null

    var discovered = true
    protected open val noEntityChar get() = "."
    protected open val noEntityEmoji get() = "\uD83D\uDD38"

    protected open val fogChar get() = " "
    protected open val fogEmoji get() = "\uD83C\uDF2B️"

    val charDisplay get()  = if (discovered) { if (entity != null) "\u001B[0;31m${entity?.charDisplay}\u001B[0m" else noEntityChar } else fogChar
    val emojiDisplay get() = if (discovered) entity?.emojiDisplay ?: noEntityEmoji else fogEmoji

    open fun isFree() = entity == null

    open var weight = MIN_WEIGHT
}