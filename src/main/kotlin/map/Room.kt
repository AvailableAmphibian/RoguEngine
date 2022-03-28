package map

import kotlin.math.max
import kotlin.math.min
import java.util.ArrayList

data class Room(
    val x1: Int,
    val x2: Int,
    val y1: Int,
    val y2: Int
) {
    /**
     * Predicate that checks if the current room intersects any other room.
     */
    fun intersectsAnyOtherRoom(rooms: ArrayList<Room>) = rooms.any {
        max(x1, it.x1) <= min(x2, it.x2) && max(y1, it.y1) <= min(y2, it.y2)
    }

    /**
     * Predicate that checks if a point is on a border of a room.
     */
    fun onBorder(it: Pair<Int, Int>):Boolean = (it.first in x1..x2 && (it.second == y1 || it.second == y2))
                || (it.second in y1 .. y2 && (it.first == x1 || it.first == x2))

    /**
     * Predicate that checks if a point is in a room.
     */
    fun containsPosition(it: Pair<Int, Int>) = it.first in x1..x2 && it.second in y1..y2

    /**
     * Returns the center of a room.
     */
    val center get() = Pair(xCenter, yCenter)
    private val xCenter get() = (x1 + x2) / 2
    private val yCenter get() = (y1 + y2) / 2
}
