package util.graph

import util.helper.calcDistance

abstract class SearchNode(
    nodeAuthorized:Boolean = false
): Comparable<SearchNode> {
    var authorized = nodeAuthorized
        protected set

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

    open fun updateNeighboursAuthorization(matrix: SearchGraph<*>){
        val (x, y) = xy
        matrix[x+1][y].lookAtNeighbours(matrix)
        matrix[x-1][y].lookAtNeighbours(matrix)
        matrix[x][y+1].lookAtNeighbours(matrix)
        matrix[x][y-1].lookAtNeighbours(matrix)
    }

    protected open fun lookAtNeighbours(matrix: SearchGraph<*>) {
        if (tileType == NodeType.ROOM_TILE
            || tileType == NodeType.ROOM_CORNER_TILE
            || tileType == NodeType.MATRIX_SIDE_TILE
            || tileType == NodeType.CORRIDOR_TILE) {
            return
        }

        val (x, y) = xy
        val below = matrix[x+1][y].isProblematic()
        val above = matrix[x-1][y].isProblematic()
        val right = matrix[x][y+1].isProblematic()
        val left = matrix[x][y-1].isProblematic()
        val downRight = matrix[x+1][y+1].isProblematic()
        val downLeft = matrix[x+1][y-1].isProblematic()
        val upRight = matrix[x-1][y+1].isProblematic()
        val upLeft = matrix[x-1][y-1].isProblematic()

        val leftColumn = left && downLeft && upLeft
        val rightColumn = right && downRight && upRight
        val lineAbove = above && upLeft && upRight
        val lineBelow = below && downLeft && downRight

        if ((leftColumn && rightColumn) || (lineAbove && lineBelow)) {
            authorized = false
            return
        }

        val upLeftCorner = if(above && upLeft &&  left) 1 else 0
        val upRightCorner = if(above && upRight && right) 1 else 0
        val downLeftCorner = if(below && downLeft && left) 1 else 0
        val downRightCorner = if(below && downRight && right) 1 else 0

        val corners = upLeftCorner + upRightCorner + downLeftCorner + downRightCorner
        authorized = corners != 0
    }

    protected open fun isProblematic():Boolean = tileType == NodeType.CORRIDOR_TILE || tileType == NodeType.ROOM_ENTRANCE_TILE

}
