package util.factories

import map.Room
import util.helper.createPairOfPoints

class RoomFactory(val matrixLength:Int, val matrixWidth: Int) {
    companion object {
        private const val MIN_WIDTH_LENGTH = 4 // inside
        private const val MIN_AREA = 16
        private const val MAX_AREA = 42

        private fun doesNotHaveLegalSize(xInside:Int, yInside:Int) = ! ((xInside >= MIN_WIDTH_LENGTH) && (yInside >= MIN_WIDTH_LENGTH) && (xInside*yInside) in MIN_AREA .. MAX_AREA)
    }

    fun createMultipleRooms(roomNumber:Int):List<Room> {
        val rooms = ArrayList<Room>()
        for (_iter in 0 until roomNumber) {
            var room = createRoom()

            while(room.intersectsAnyOtherRoom(rooms))
                room = createRoom()

            rooms.add(room)
        }

        return rooms
    }

    private fun createRoom():Room {
        val (x1,x2) = createPairOfPoints(matrixLength)
        val (y1,y2) = createPairOfPoints(matrixWidth)
        val xInside = x2 - x1 - 2
        val yInside = y2 - y1 - 2

        if (doesNotHaveLegalSize(xInside, yInside)){
            return createRoom()
        }
        return Room(x1, x2, y1, y2)
    }
}