package util.helper

import entities.Entity
import map.Room
import kotlin.math.*
import kotlin.random.Random

/**
 * Function used with the exceptions to get a name not influenced by the `toString` method.
 */
fun entityToString(entity: Entity<*>) = entity.javaClass.name +"@"+Integer.toHexString(entity.hashCode())

typealias CorridorCreator = (Int, Int, Int) -> Unit

private const val BASE_ANGLE_NUMBER = 0
private const val UP_ANGLE_NUMBER = 1
private const val MAX_ANGLE_NUMBER = 3

private fun getAngleDivider(angleNumber:Int) = when(angleNumber) {
        0 -> 1
        else -> (angleNumber + 1) * 2
    }

/**
 * Function used to create angles between corridors.
 *
 * @param r1 the first room
 * @param r2 the second room
 *
 * @return a pair of lists containing the different points of the corridors
 */
fun addAngles(r1: Room, r2: Room, moreHorizontal:Boolean): Pair<ArrayList<Int>, ArrayList<Int>> {
    val minX = min(r1.xCenter, r2.xCenter)
    val minY = min(r1.yCenter, r2.yCenter)
    val maxX = max(r1.xCenter, r2.xCenter)
    val maxY = max(r1.yCenter, r2.yCenter)

    val listX = ArrayList<Int>()
    val listY = ArrayList<Int>()
    val anglesNumber = Random.Default.nextInt(MAX_ANGLE_NUMBER)
//    val anglesNumber = 0

    val anglesNumberX = anglesNumber + if (moreHorizontal) UP_ANGLE_NUMBER else BASE_ANGLE_NUMBER
    val anglesNumberY = anglesNumber + if (moreHorizontal) BASE_ANGLE_NUMBER else UP_ANGLE_NUMBER

    val cutX = (minX + maxX).toFloat() / getAngleDivider(anglesNumberX)
    val cutY = (minY + maxY).toFloat() / getAngleDivider(anglesNumberY)


    for (cutNumber in 0 .. anglesNumberX) {
        val cut = (cutX * cutNumber).toInt()
        listX += cut + minX
    }
    for (cutNumber in 0 ..  anglesNumberY) {
        val cut = (cutY * cutNumber).toInt()
        listY += cut + minY
    }

    listX += maxX
    listY += maxY

    return Pair(listX, listY)
}

fun createPairOfPoints(matrixDimension:Int):Pair<Int,Int> {
    val x1 = Random.Default.nextInt(matrixDimension)
    val x2 = Random.Default.nextInt(matrixDimension)

    return if (x1 > x2) Pair(x2, x1) else Pair(x1, x2)
}

private fun reworkedAbs(n:Int) = if (n <= 0) n else abs(n) - 1
private fun Int.square() = this.toDouble().pow(2)
private fun calcDistance(a: Int, b:Int) = sqrt(a.square() + b.square()).roundToInt()

fun calcDistance(fstPair: Pair<Int, Int>, sndPair: Pair<Int, Int>): Int {
    val a = reworkedAbs(fstPair.first - sndPair.first)
    val b = reworkedAbs(fstPair.second - sndPair.second)

    return calcDistance(a, b)
}
