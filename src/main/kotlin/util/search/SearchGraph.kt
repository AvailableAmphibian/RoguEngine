package util.search

class SearchGraph(private val length:Int, private val width:Int) {
    private var map: Array<Array<AStarNode?>> = Array (length) { Array(width) { null } }

    operator fun get(x: Int) = map[x]
    operator fun get(xy: Pair<Int, Int>) = map[xy.first][xy.second]!!

    fun initMap(end:Pair<Int, Int>) {
        for (i in 0 until length)
            for (j in 0 until width) {
                map[i][j] = AStarNode(1.0, true, Pair(i,j), end)
            }
    }

    fun getCellsAround(xy: Pair<Int, Int>): List<AStarNode> {
        val cells = mutableListOf<AStarNode>()
        val (x,y) = xy
        if (x-1 > 0 && this[x-1][y]!!.authorized)
            cells += this[x-1][y]!!
        if (x+1 < length-1 && this[x+1][y]!!.authorized)
            cells += this[x+1][y]!!
        if (y-1 > 0
            && this[x][y-1]!!.authorized) cells += this[x][y-1]!!
        if (y+1 < width-1 && this[x][y+1]!!.authorized)
            cells += this[x][y+1]!!

        return cells
    }
}