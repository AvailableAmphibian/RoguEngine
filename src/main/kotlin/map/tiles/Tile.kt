package map.tiles

import entities.Entity

open class Tile(var entity:Entity<*>?) {
    var discovered = false
    protected open val noEntityChar get() = "."
    protected open val noEntityEmoji get() = "\uD83D\uDD38"

    protected open val fogChar get() = " "
    protected open val fogEmoji get() = "\uD83C\uDF2B️"

    val charDisplay get()  = if (discovered) entity?.charDisplay ?: noEntityChar else fogChar
    val emojiDisplay get() = if (discovered) entity?.emojiDisplay ?: noEntityEmoji else fogEmoji

    open fun isFree() = entity == null
}