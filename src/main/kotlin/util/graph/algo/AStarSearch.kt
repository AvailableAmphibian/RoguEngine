package util.graph.algo

import util.graph.SearchGraph
import util.graph.SearchNode

abstract class AStarSearch<TNode: SearchNode, TGraph: SearchGraph<TNode>> {
    val visited = mutableMapOf<Pair<Int,Int>, Pair<Int, Int>>()
    val inQueue = mutableListOf<TNode>()
    lateinit var source: Pair<Int, Int>
    lateinit var end: Pair<Int, Int>
    val map: TGraph by lazy { initGraph() }

    companion object {
        val previousForSource = Pair(-1,-1)
    }

    abstract fun initGraph(): TGraph

    fun addSourceToQueue() = with(map[source]){
            inQueue.add(this)
            this.previousNodePosition = previousForSource
            this.gCost = 0
        }


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

    fun computeIteration():Boolean {
        val current = getNext()
        inQueue.remove(current)

        visited[current.xy] = current.previousNodePosition

        if (current.xy == end)
            return true

        val around = map.getCellsAround(current.xy)
        around.forEach {
            if (visited[it.xy] == null && map[current.xy].authorized) {
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

    fun getShortestPath(): List<Pair<Int, Int>> {
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