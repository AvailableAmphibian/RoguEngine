package map

import entities.Entity
import entities.Player
import map.tiles.Tile
import map.tiles.Wall
import util.factories.RoomFactory
import util.helper.TileMatrix
import kotlin.random.Random

class Stage(/*val rooms: List<Room>, val roomLinks:Map<Int, List<Int>>*/) {
    val map = TileMatrix(32, 62)
    private val entities = ArrayList<Entity<*>>()
    private val roomFactory = RoomFactory(map.length, map.width)

    fun initMap() {
        val rooms = roomFactory.createMultipleRooms(5)
        rooms.forEach { map.applyRoom(it) }
        for (i in 0 until 4){
            map.createCorridor(rooms[i], rooms[i + 1])
        }
    }

    fun playTurn(){
        entities.forEach {
            it.takeTurn(map)
        }
    }
}
