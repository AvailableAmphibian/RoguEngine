package util.graph.architest

import util.helper.calcDistance
import util.search.model.AStarNode
import util.search.model.NodeType

abstract class SearchNode(
    val authorized:Boolean = false
): Comparable<SearchNode> {

    protected var weight:Double = if (authorized) DEFAULT_WEIGHT else WEIGHT_LESS

    lateinit var xy: Pair<Int, Int>
    lateinit var end: Pair<Int, Int>
    lateinit var previousNodePosition: Pair<Int, Int>

    var gCost: Int = Int.MAX_VALUE
    private val hCost get() = calcDistance(xy, end)
    private val fCost get() = gCost + (weight * hCost)
    lateinit var tileType: NodeType

    companion object {
        const val WEIGHT_LESS = -1.0
        const val DEFAULT_WEIGHT = 1.0
        const val SMALL_WEIGHT = 1.5
    }

    override fun compareTo(other: SearchNode): Int {
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
