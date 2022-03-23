package util.graph.algo

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import util.graph.SearchGraph
import util.graph.SearchNode
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
abstract class TestAStarSearch<TNode: SearchNode, TGraph: SearchGraph<TNode>> {
    @MockK
    lateinit var searcher: AStarSearch<TNode, TGraph>

    @BeforeEach
    fun initSearcher() {
        searcher = createSearcher()
    }

    abstract fun createSearcher(): AStarSearch<TNode, TGraph>
    abstract fun setAWall()

    @Test
    fun `test iterations on small straight line`() {
        // Initializing things related to this test
        with(searcher) {
            source = Pair(2, 1)
            end = Pair(2, 4)
            map.initMap(end)

            addSourceToQueue()
        }

        // Verifying we match the starting state
        assertEquals(0, searcher.visited.size)
        assertEquals(1, searcher.inQueue.size)

        // Verifying everything matches the first iteration
        val firstIter = searcher.computeIteration()
        assertFalse(firstIter)
        assertEquals(1, searcher.visited.size)
        assertTrue(searcher.visited.contains(Pair(2, 1)))
        assertEquals(3, searcher.inQueue.size)
        assertTrue(searcher.inQueue.contains(searcher.map[Pair(2, 2)]))
        assertTrue(searcher.inQueue.contains(searcher.map[Pair(3, 1)]))
        assertTrue(searcher.inQueue.contains(searcher.map[Pair(1, 1)]))
        assertFalse(searcher.inQueue.contains(searcher.map[Pair(2, 0)]))

        // Verifying everything matches the second iteration
        val secondIter = searcher.computeIteration()
        assertFalse(secondIter)
        assertEquals(2, searcher.visited.size)
        assertTrue(searcher.visited.contains(Pair(2, 2)))
        assertEquals(5, searcher.inQueue.size)
        assertFalse(searcher.inQueue.contains(searcher.map[Pair(2, 2)]))
        assertFalse(searcher.inQueue.contains(searcher.map[Pair(2, 1)]))
        assertTrue(searcher.inQueue.contains(searcher.map[Pair(2, 3)]))
        assertTrue(searcher.inQueue.contains(searcher.map[Pair(1, 2)]))
        assertTrue(searcher.inQueue.contains(searcher.map[Pair(3, 2)]))

        // Verifying everything matches the third iteration
        val thirdIter = searcher.computeIteration()
        assertFalse(thirdIter)
        assertEquals(3, searcher.visited.size)
        assertTrue(searcher.visited.contains(Pair(2, 3)))
        assertEquals(7, searcher.inQueue.size)
        assertFalse(searcher.inQueue.contains(searcher.map[Pair(2, 2)]))
        assertFalse(searcher.inQueue.contains(searcher.map[Pair(2, 3)]))
        assertTrue(searcher.inQueue.contains(searcher.map[Pair(2, 4)]))
        assertTrue(searcher.inQueue.contains(searcher.map[Pair(1, 3)]))
        assertTrue(searcher.inQueue.contains(searcher.map[Pair(3, 3)]))

        // Verifying everything matches the fourth iteration
        val fourthIter = searcher.computeIteration()
        assertTrue(fourthIter)
        assertEquals(4, searcher.visited.size)
        assertTrue(searcher.visited.contains(Pair(2, 4)))
        assertEquals(6, searcher.inQueue.size)
        assertFalse(searcher.inQueue.contains(searcher.map[Pair(2, 4)]))
        assertFalse(searcher.inQueue.contains(searcher.map[Pair(2, 3)]))
        assertFalse(searcher.inQueue.contains(searcher.map[Pair(1, 4)]))
        assertFalse(searcher.inQueue.contains(searcher.map[Pair(3, 4)]))
        assertFalse(searcher.inQueue.contains(searcher.map[Pair(2, 5)]))
    }

    @Test
    fun `testing if a wall forces verifying multiple directions`() {
        with(searcher) {
            source = Pair(3, 1)
            end = Pair(2, 4)
            map.initMap(end)

            setAWall()

            addSourceToQueue()
        }

        assertEquals(0, searcher.visited.size)
        assertEquals(1, searcher.inQueue.size)
        val fst = searcher.computeIteration()
        assertFalse(fst)
        assertEquals(1, searcher.visited.size)
        assertEquals(2, searcher.inQueue.size)
        assertTrue(searcher.visited.contains(searcher.source))
        assertFalse(searcher.inQueue.contains(searcher.map[searcher.source]))
        val snd = searcher.computeIteration()
        assertFalse(snd)
        assertEquals(2, searcher.visited.size)
        assertEquals(2, searcher.inQueue.size)
        assertTrue(
            searcher.visited.contains(Pair(2, 1))
                    || searcher.visited.contains(Pair(4, 1)),
            "Should contain one of the two nodes near the starting node"
        )
        val trd = searcher.computeIteration()
        assertFalse(trd)
        assertEquals(3, searcher.visited.size)
        assertEquals(2, searcher.inQueue.size)
        assertTrue(
            searcher.visited.contains(Pair(2, 1))
                    && searcher.visited.contains(Pair(4, 1)),
            "Should contain both of the two nodes near the starting node"

        )
        val fth = searcher.computeIteration()
        assertFalse(fth)
        assertEquals(4, searcher.visited.size)
        assertEquals(2, searcher.inQueue.size)
    }

    @Test
    fun `testing if a wall and a weight forces a path`() {
        with(searcher) {
            source = Pair(3, 1)
            end = Pair(2, 4)
            map.initMap(end)

            setAWall()

            map[4][1].addMoreWeight()
            map[5][1].addMoreWeight()

            addSourceToQueue()
        }

        assertEquals(0, searcher.visited.size)
        assertEquals(1, searcher.inQueue.size)
        val fst = searcher.computeIteration()
        assertFalse(fst)
        assertEquals(1, searcher.visited.size)
        assertEquals(2, searcher.inQueue.size)
        assertTrue(searcher.visited.contains(searcher.source))
        assertFalse(searcher.inQueue.contains(searcher.map[searcher.source]))
        val snd = searcher.computeIteration()
        assertFalse(snd)
        assertEquals(2, searcher.visited.size)
        assertEquals(2, searcher.inQueue.size)
        assertTrue(
            searcher.visited.contains(Pair(2, 1)),
            "Should contain the node above the starting node"
        )
        val trd = searcher.computeIteration()
        assertFalse(trd)
        assertEquals(3, searcher.visited.size)
        assertEquals(2, searcher.inQueue.size)
        assertTrue(searcher.visited.contains(Pair(1, 1)))
    }

    @Test
    fun `testing if the shortest path returned is the good one`() {
        // Initializing test related things
        with(searcher) {
            end = Pair(5, 5)
            // Path is :
            // 0,0 - 1,1 - 3,3 - 0,12 - 21,0 - 5,5
            visited[Pair(0, 0)] = Pair(-1, -1)
            visited[Pair(1, 1)] = Pair(0, 0)
            visited[Pair(2, 2)] = Pair(0, 0)
            visited[Pair(3, 3)] = Pair(1, 1)
            visited[Pair(0, 1)] = Pair(0, 12)
            visited[Pair(0, 12)] = Pair(3, 3)
            visited[Pair(1, 2)] = Pair(3, 3)
            visited[Pair(5, 3)] = Pair(0, 0)
            visited[Pair(4, 2)] = Pair(2, 4)
            visited[Pair(2, 4)] = Pair(4, 2)
            visited[Pair(12, 0)] = Pair(3, 3)
            visited[Pair(21, 0)] = Pair(0, 12)
            visited[Pair(0, 8)] = Pair(0, 0)
            visited[searcher.end] = Pair(21, 0)
        }

        // Expected output
        val expected = listOf(
            Pair(0, 0),
            Pair(1, 1),
            Pair(3, 3),
            Pair(0, 12),
            Pair(21, 0),
            searcher.end
        )

        val actual = searcher.getShortestPath()

        assertTrue(expected.containsAll(actual), "Expected should contain every element of actual")
        assertTrue(actual.containsAll(expected), "And actual should contain every element of expected")

        assertFalse(actual.contains(Pair(2, 2)), "Expected should not contain the pair (2,2)")
        assertFalse(actual.contains(Pair(12, 0)), "Expected should not contain the pair (12,0)")
        assertFalse(actual.contains(Pair(0, 8)), "Expected should not contain the pair (0,8)")

        assertEquals(6, actual.size, "We expect that the list contains nothing else than 6 elements")
    }

    @Test
    fun `trying to create a full path `() {
        with(searcher) {
            source = Pair(3, 1)
            end = Pair(3, 4)
            map.initMap(end)


            setAWall()

            addSourceToQueue()
        }

        searcher.compute()

        assertEquals(13, searcher.visited.size, "Should find that 13 nodes where visited (A* and not BestFirst)")
        val path = searcher.getShortestPath()
        assertEquals(8, path.size, "Should find out that the shortest path is composed of 8 nodes (so 7 vertices)")
    }
}
