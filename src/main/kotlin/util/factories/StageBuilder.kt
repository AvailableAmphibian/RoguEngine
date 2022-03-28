package util.factories

import entities.Player
import entities.monsters.Dragon
import map.Room
import map.Stage
import util.factories.monster.ImpFactory
import util.factories.monster.SpiderFactory
import util.factories.monster.WolfFactory
import util.graph.algo.CorridorGenerator
import util.graph.tiles.TileMatrix

/**
 * Builder class used to create new stages.
 */
class StageBuilder {
    companion object {
        /**
         * This part could be better using the reflection API.
         */
        val diffSpawners = mutableListOf<MonsterFactory>().apply {
            add(SpiderFactory())
            add(ImpFactory())
            add(WolfFactory())
        }
    }

    private val stage = Stage()
    private var length = 20
    private var width = 20
    private var roomQuantity = 3
    private var stageNumber = 0
    private var player: Player? = null
    private var dragon: Dragon? = null

    fun setMatrixLength(length: Int): StageBuilder {
        this.length = length
        return this
    }

    fun setMatrixWidth(width: Int): StageBuilder {
        this.width = width
        return this
    }

    fun setRoomNumber(roomQuantity: Int): StageBuilder {
        this.roomQuantity = roomQuantity
        return this
    }

    /**
     * Used to complexify a bit the stages created.
     */
    fun setStageNumber(stage: Int): StageBuilder {
        this.stageNumber = stage
        return this
    }

    /**
     * Set a player to the next stage.
     */
    fun setPlayer(p: Player): StageBuilder {
        this.player = p
        return this
    }

    private fun createTileMatrix(rooms: List<Room>): TileMatrix {
        val tileMatrix = TileMatrix(length = length, width = width)
        tileMatrix.initMap(Pair(0, 0)) // todo : fix end
        rooms.forEach { tileMatrix.applyRoomToMap(it) }
        return tileMatrix
    }

    /**
     * Creating some rooms for the current TileMatrix.
     */
    private fun createRooms(): List<Room> {
        val factory = RoomFactory(length, width)
        return factory.createMultipleRooms(roomQuantity)
    }

    /**
     * Linking some rooms.
     */
    private fun createCorridors(rooms: List<Room>, tileMatrix: TileMatrix) {
        val corridorGenerator = CorridorGenerator().apply {
            this.tileMatrix = tileMatrix
            this.rooms = rooms
        }

        corridorGenerator.linkRooms()
    }

    /**
     * Creating some spawners for the current stage.
     */
    private fun createSpawners(): Spawners {
        return Spawners().apply {
            repeat(2) {
                add(diffSpawners.random())
            }
        }
    }

    /**
     * Retrieving the stage containing everything we needed.
     */
    fun build(): Stage {
        val rooms = createRooms()
        val tileMatrix = createTileMatrix(rooms)
        if (this.player == null)
            this.player = Player(rooms[0].center)
        if (this.dragon == null)
            this.dragon = Dragon(rooms.last().center)

        createCorridors(rooms, tileMatrix)
        val monsterFactories = createSpawners()
        tileMatrix[this.player!!.position].nEntState.value = this.player
        return stage.apply {
            this.player = this@StageBuilder.player!!
            this.dragon = this@StageBuilder.dragon!!

            this.map = (tileMatrix)
            this.spawners = monsterFactories
        }
    }


}