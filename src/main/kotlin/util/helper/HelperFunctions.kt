package util.helper

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import entities.Entity
import map.Room
import util.graph.SearchGraph
import util.graph.SearchNode
import util.graph.tiles.TileMatrix
import kotlin.math.*
import kotlin.random.Random

/**
 * TypeAlias made for easier lambda creation
 */
typealias NodesGetter<T> = (Pair<Int, Int>, SearchGraph<T>) -> List<T>

val emojiFont = FontFamily(
    Font(
        resource = "fonts/NotoColorEmoji.ttf",
        weight = FontWeight.W400,
        style = FontStyle.Normal
    )
)

/**
 * Function used with the exceptions to get a name not influenced by the `toString` method.
 */
fun entityToString(entity: Entity<*>) = entity.javaClass.name +"@"+Integer.toHexString(entity.hashCode())

/**
 * Create a random pair of point in a given matrix.
 */
fun createPairOfPoints(matrixLength:Int, matrixWidth:Int = matrixLength):Pair<Int,Int> {
    val x1 = Random.Default.nextInt(matrixLength)
    val x2 = Random.Default.nextInt(matrixWidth)

    return if (x1 > x2) Pair(x2, x1) else Pair(x1, x2)
}


/**
 * Extension function to take the square of an Int.
 */
private fun Int.square() = this.toDouble().pow(2)
private fun calcDistance(a: Int, b:Int) = sqrt(a.square() + b.square()).roundToInt()
private fun reworkedAbs(n:Int) = if (n <= 0) n else abs(n) - 1

/**
 * Calculate the distance between 2 points, works for this use case.
 */
fun calcDistance(fstPair: Pair<Int, Int>, sndPair: Pair<Int, Int>): Int {
    val a = reworkedAbs(fstPair.first - sndPair.first)
    val b = reworkedAbs(fstPair.second - sndPair.second)

    return calcDistance(a, b)
}
