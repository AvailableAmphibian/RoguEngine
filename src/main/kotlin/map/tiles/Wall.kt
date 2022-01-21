package map.tiles

class Wall: Tile(null) {
    override val noEntityDisplay: String
        get() = "#"

    override fun isFree() = false
}