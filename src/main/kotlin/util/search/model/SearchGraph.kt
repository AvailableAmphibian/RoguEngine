package util.search.model

abstract class SearchGraph<T>(protected val length:Int, protected val width:Int) {
    protected var map: Array<Array<AStarNode?>> = Array (length) { Array(width) { null } }

    operator fun get(x: Int) = map[x]
    operator fun get(xy: Pair<Int, Int>) = map[xy.first][xy.second]!!

    abstract fun initMap()
    abstract fun updateMap()


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