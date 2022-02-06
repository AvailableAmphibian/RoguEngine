package util.search

import util.helper.TileMatrix

class ASSearch {
    val visited = mutableMapOf<Pair<Int,Int>, Pair<Int, Int>>()
    val inQueue = mutableListOf<AStarNode>()
    lateinit var source: Pair<Int, Int>
    lateinit var end: Pair<Int, Int>
    lateinit var map: SearchGraph
    lateinit var matrix: TileMatrix

    fun compute() {
        var found = false
        while (!found) {
            found = computeIteration()
        }
    }

    fun getNext() = inQueue.stream().min(AStarNode::compareTo).get()


    fun computeIteration():Boolean {
        val current = getNext()
        inQueue.remove(current)

        visited[current.xy] = current.previousNodePosition

        if (current.xy == end)
            return true

        val around = map.getCellsAround(current.xy)
        around.forEach {
            if (visited[it.xy] == null && matrix.authorizePosition(current.xy)) {
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
        val last = Pair(-1, -1)
        while (pos!! != last) {
            path += pos
            pos = visited[pos]
        }
        return path
    }

}