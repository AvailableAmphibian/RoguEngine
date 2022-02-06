package map.tiles

class Wall: Tile(null) {
    override val noEntityChar get() = "#"
    override val noEntityEmoji get() = "⬛"

    override fun isFree() = false

    init {
        weight = Tile.NO_WEIGHT
    }
}