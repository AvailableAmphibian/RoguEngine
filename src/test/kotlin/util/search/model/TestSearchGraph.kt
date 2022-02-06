package util.search.model

import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class TestSearchGraph {
    @MockK
    lateinit var graph: SearchGraph<Any?>

    @BeforeEach
    fun createGraph() {
        graph = initGraph()
    }

    abstract fun initGraph(): SearchGraph<Any?>

    @Test
    abstract fun testInitMap()
}
