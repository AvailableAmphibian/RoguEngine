package util.search

import util.helper.calcDistance
import kotlin.math.abs


class AStarNode(
    var w: Double,
    val authorized: Boolean,
    val xy: Pair<Int, Int>,
    end: Pair<Int, Int>
) : Comparable<AStarNode> {
    var gCost: Int = Int.MAX_VALUE
    val hCost = calcDistance(xy, end)
    val fCost get() = gCost + (w * hCost)

    lateinit var previousNodePosition: Pair<Int, Int>

    init {
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
