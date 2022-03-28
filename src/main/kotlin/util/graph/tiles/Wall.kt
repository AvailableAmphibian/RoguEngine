package util.graph.tiles

/**
 * Tile that can't be walked on, default tile in TileMatrices.
 */
class Wall: Tile(false) {
    init {
        weight = WEIGHT_LESS
    }

    override val charDisplay: Char
        get() = '#'
    override val emojiDisplay: String
        get() = "⬛"
}