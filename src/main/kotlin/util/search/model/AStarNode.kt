package util.search.model

import map.tiles.Tile
import util.helper.calcDistance


class AStarNode(
    var w: Double,
    val authorized: Boolean,
    val xy: Pair<Int, Int>,
) : Comparable<AStarNode> {
    lateinit var end: Pair<Int, Int>
    var gCost: Int = Int.MAX_VALUE
    val hCost get() = calcDistance(xy, end)
    val fCost get() = gCost + (w * hCost)

    lateinit var tileType:NodeType
    lateinit var previousNodePosition: Pair<Int, Int>

    companion object {
        fun createUnauthorizedNode(pos:Pair<Int, Int>) = AStarNode(Tile.NO_WEIGHT, false, pos)
    }

    override fun compareTo(other: AStarNode): Int {
        if (fCost == other.fCost)
            return hCost.compareTo(other.hCost)
        return fCost.compareTo(other.fCost)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        val node = other as AStarNode

        return xy == node.xy && hCost == node.hCost
    }

    override fun hashCode(): Int {
        var result = xy.hashCode()
        result = 31 * result + hCost
        return result
    }


}
