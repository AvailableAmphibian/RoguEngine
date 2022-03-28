package util.graph.algo

import util.graph.SearchGraph
import util.graph.SearchNode
import util.graph.tiles.TileMatrix
import util.helper.NodesGetter

/**
 * Generic A* algorithm.
 */
abstract class AStarSearch<TNode: SearchNode, TGraph: SearchGraph<TNode>> {
    val visited = mutableMapOf<Pair<Int,Int>, Pair<Int, Int>>()
    val inQueue = mutableListOf<TNode>()
    open lateinit var source: Pair<Int, Int>
    open lateinit var end: Pair<Int, Int>
    val map: TGraph by lazy { initGraph() }
    open val nodesGetter:NodesGetter<TNode> =  { xy, map -> map.getCellsAround(xy) }

    companion object {
        val previousForSource = Pair(-1,-1)
    }

    abstract fun initGraph(): TGraph

    fun addSourceToQueue() = with(map[source]){
            inQueue.add(this)
            this.previousNodePosition = previousForSource
            this.gCost = 0
        }

    /**
     * Runs A* between two points.
     */
    fun compute() {
        this.inQueue.clear()
        this.visited.clear()
        addSourceToQueue()
        var found = false
        while (!found) {
            found = computeIteration()
        }
    }

    private fun getNext() = inQueue.stream().min{ aNode, anotherNode -> aNode.compareTo(anotherNode) }.get()

    /**
     * Computes one iteration of A*.
     *
     * @return true if we found the finishing node, otherwise false.
     */
    fun computeIteration():Boolean {
        val current = getNext()
        inQueue.remove(current)

        visited[current.xy] = current.previousNodePosition

        if (current.xy == end)
            return true

        val around = nodesGetter.invoke(current.xy, map)
        around.forEach {
            if (visited[it.xy] == null && map[it.xy].authorized) {
                val currentGCost = current.gCost + 1
                if (!inQueue.contains(it) || it.gCost < currentGCost) {
                    it.gCost = currentGCost
                    it.previousNodePosition = current.xy
                    inQueue.add(it)
                }
            }
        }
        return false
    }

    /**
     * Returns the shortest path between two points.
     */
    open fun getShortestPath(): List<Pair<Int, Int>> {
        val path = mutableListOf(end)
        var pos = visited[end]
        val last = previousForSource
        while (pos!! != last) {
            path += pos
            pos = visited[pos]
        }
        return path
    }
}
