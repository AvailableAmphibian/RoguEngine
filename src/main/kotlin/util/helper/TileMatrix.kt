package util.helper

import map.tiles.Tile

/**
 * Helper class used to have a matrix of tiles.
 *
 * Delegated the useful methods that we need from the first array.
 */
class TileMatrix(private val _length:Int, private val _width:Int) {
    val length get() = _length
    val width get() = _width

    fun outOfMap(x:Int, y:Int):Boolean = x < 0 || y < 0 || x >= length || y >= width

    private val matrix = Array(length) { Array(width){ Tile(null)} }

    operator fun get(index: Int) = matrix[index]
    operator fun iterator() = matrix.iterator()
}