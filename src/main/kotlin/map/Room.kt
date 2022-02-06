package map

import java.util.ArrayList

data class Room(
    val x1: Int,
    val x2: Int,
    val y1: Int,
    val y2: Int
) {
    fun intersectsAnyOtherRoom(rooms: ArrayList<Room>) = rooms.any {
        (x1 in it.x1 .. it.x2 || x2 in it.x1 .. it.x2) &&
        (y1 in it.y1 .. it.y2 || y2 in it.y1 .. it.y2)
    }

    val xCenter get() = (x1 + x2) / 2
    val yCenter get() = (y1 + y2) / 2
    val center get() = Pair(xCenter, yCenter)
}
