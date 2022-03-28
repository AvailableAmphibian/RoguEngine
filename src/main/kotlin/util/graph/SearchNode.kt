package util.graph

import util.helper.calcDistance

abstract class SearchNode(
    val authorized:Boolean
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
        const val SOME_WEIGHT = 0.4
    }

    /**
     * Indexing nodes according to fcost then hcost then the closest to the left or top border
     */
    override fun compareTo(other: SearchNode): Int {
        if (fCost == other.fCost) {
            if (hCost == other.hCost) {
                val thisLowest = if (xy.first > xy.second) xy.second else xy.first
                val otherLowest = if (other.xy.first > other.xy.second) other.xy.second else other.xy.first
                return thisLowest.compareTo(otherLowest)
            }
            return hCost.compareTo(other.hCost)
        }
        return fCost.compareTo(other.fCost)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        val node = other as SearchNode

        return xy == node.xy
    }

    override fun hashCode(): Int {
        return xy.hashCode()
    }

    open fun addSomeWeight() {
        weight += SOME_WEIGHT
    }

    open fun addMoreWeight() {
        addSomeWeight()
        addSomeWeight()
    }
}
