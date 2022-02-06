package util.factories

import map.Room
import map.Stage
import util.graph.architest.algo.CorridorGenerator
import util.graph.architest.tiles.TM

/**
 * Builder class used to create new stages.
 */
class StageBuilder {
    private val stage = Stage()
    private var length = 20
    private var width = 20
    private var roomQuantity = 3
    private var stageNumber = 0

    fun setMatrixLength(length:Int):StageBuilder {
        this.length = length
        return this
    }

    fun setMatrixWidth(width:Int):StageBuilder {
        this.width = width
        return this
    }

    fun setRoomNumber(roomQuantity:Int):StageBuilder {
        this.roomQuantity = roomQuantity
        return this
    }

    private fun createTileMatrix(rooms:List<Room>): TM {
        val tm = TM(length = length, width = width)
        tm.initMap()
        rooms.forEach { tm.applyRoomToMap(it) }
        return tm
    }

    private fun createRooms(): List<Room> {
        val factory = RoomFactory(length, width)
        return factory.createMultipleRooms(roomQuantity)
    }

    private fun createCorridors(rooms:List<Room>, tileMatrix: TM) {
        val corridorGenerator = CorridorGenerator().apply {
            this.tileMatrix = tileMatrix
            this.rooms = rooms
        }

        corridorGenerator.linkRooms()
    }

    private fun createSpawners(): List<MonsterFactory> {
        return emptyList() //todo: update when we create the MonsterFactory classes
    }

    /**
     * Retrieving the stage containing everything we needed.
     */
    fun build(): Stage {
        val rooms = createRooms()
        val tileMatrix = createTileMatrix(rooms)

        createCorridors(rooms, tileMatrix)
        val monsterFactories = createSpawners()
        return stage.apply {
//            TODO("Reflect this change")
//             this.map = tileMatrix
            // this.spawners = monsterFactories
        }
    }


}