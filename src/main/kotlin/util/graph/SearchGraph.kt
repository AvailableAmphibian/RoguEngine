package util.graph


abstract class SearchGraph<T: SearchNode>(
    val length:Int = 20,
    val width:Int = 20) {
    protected val matrix: Array<Array<T>> by lazy { createDefaultMatrix() }

    protected abstract fun initMap()

    /**
     * Must be used to define a generic matrix (need for A*).
     */
    abstract fun createDefaultMatrix():Array<Array<T>>

    /**
     * Return a row of the matrix.
     */
    operator fun get(x:Int):Array<T> = matrix[x]

    /**
     * Return a tile of the matrix.
     */
    operator fun get(xy: Pair<Int, Int>):T = matrix[xy.first][xy.second]
    operator fun iterator() = matrix.iterator()

    fun initMap(end:Pair<Int, Int>) {
        initMap()
        setEndOnEachCell(end)
    }

    /**
     * Return every node connected to a given node.
     */
    fun getCellsAround(xy: Pair<Int, Int>): List<T> {
        val cells = mutableListOf<T>()
        val (x,y) = xy
        if (x-1 > 0 && this[x-1][y].authorized)
            cells += this[x-1][y]
        if (x+1 < length-1 && this[x+1][y].authorized)
            cells += this[x+1][y]
        if (y-1 > 0 && this[x][y-1].authorized)
            cells += this[x][y-1]
        if (y+1 < width-1 && this[x][y+1].authorized)
            cells += this[x][y+1]

        return cells
    }

    protected fun setEndOnEachCell(xy: Pair<Int, Int>) = iterator().forEach { line -> line.forEach { it.end = xy } }

    /**
     * Apply a given path to the current graph.
     */
    abstract fun applyPath(shortestPath: List<Pair<Int, Int>>)

}
