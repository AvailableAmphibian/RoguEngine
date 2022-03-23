package util.search.model
//
//import io.mockk.junit5.MockKExtension
//import map.Room
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import util.graph.SearchGraph
//import util.graph.corridorgen.CorridorGenerationGraph
//import util.graph.tiles.TileMatrix
//import kotlin.test.assertFalse
//import kotlin.test.assertTrue
//
//@ExtendWith(MockKExtension::class)
//class TestCorridorGenerationGraph: TestSearchGraph() {
//    private val rooms get() = listOf(
//            Room(1,6, 1, 6),
//            Room(10,16, 1, 6),
//            Room(1,6, 10, 16),
//            Room(10,16, 10,16)
//    )
//    // The created matrix should be something like that :
//    // ##################
//    // ##################
//    // ##....######....##
//    // ##....######....##
//    // ##....######....##
//    // ##....######....##
//    // ##################
//    // ##################
//    // ##################
//    // ##################
//    // ##################
//    // ##....######....##
//    // ##....######....##
//    // ##....######....##
//    // ##....######....##
//    // ##################
//    // ##################
//    override fun initGraph(): SearchGraph<*> {
//        val matrix = TileMatrix(20, 20)
////        rooms.forEach { matrix.applyRoom(it) }
////        graph = CorridorGenerationGraph(matrix, rooms)
//        return graph
//    }
//
//    @Test
//    override fun testInitMap() {
//        graph.initMap()
//
//        // ---- Testing the matrix part ----
//        // The matrix used for the tests is a squared matrix, so we can use
//        // only one loop for the sides of the matrix
//        for (i in 0 until 20) {
//            assertFalse(graph[i][0]!!.authorized)
//            assertFalse(graph[i][19]!!.authorized)
//            assertFalse(graph[0][i]!!.authorized)
//            assertFalse(graph[19][i]!!.authorized)
//        }
//
//
//        // ---- Testing the room part ----
//        // Verifying if the rooms are applied correctly
//
//        // Room(1,6,1,6)
//
//        // Any
//        assertFalse(graph[1][1]!!.authorized
//                || graph[1][2]!!.authorized
//                || graph[1][5]!!.authorized
//                || graph[1][6]!!.authorized
//
//                || graph[2][1]!!.authorized
//                || graph[2][6]!!.authorized
//                || graph[5][1]!!.authorized
//                || graph[5][6]!!.authorized
//
//                || graph[6][1]!!.authorized
//                || graph[6][2]!!.authorized
//                || graph[6][5]!!.authorized
//                || graph[6][6]!!.authorized
//        )
//
//        // All
//        assertTrue(graph[1][3]!!.authorized
//                && graph[1][4]!!.authorized
//                && graph[6][3]!!.authorized
//                && graph[6][4]!!.authorized
//
//                && graph[3][1]!!.authorized
//                && graph[4][1]!!.authorized
//                && graph[3][6]!!.authorized
//                && graph[4][6]!!.authorized
//        )
//
//        // Looping
//        for (i in 2 .. 5) {
//            for (j in 2 .. 5) {
//                assertTrue(graph[i][j]!!.authorized)
//            }
//        }
//
//        // Between two rooms
//        //Room(1,6, 1, 6),
//        //Room(10,16, 1, 6)
//
//        for (i in 7 until 10) {
//            for (j in 1 .. 6) {
//                assertTrue(graph[i][j]!!.authorized)
//            }
//        }
//
//    }
//}
