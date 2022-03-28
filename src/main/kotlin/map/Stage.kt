package map

import entities.Entity
import entities.Player
import entities.monsters.Dragon
import entities.monsters.Imp
import entities.monsters.Spider
import entities.monsters.Wolf
import util.factories.MonsterFactory
import util.factories.Spawners
import util.graph.tiles.TileMatrix
import util.player_action.PlayerAction

class Stage {
    lateinit var spawners: Spawners
    lateinit var player: Player
    lateinit var dragon: Dragon
    lateinit var map: TileMatrix
    val entities by lazy { mutableListOf<Entity<*>>().apply { add(player) } }

    private var turnNumber = 0

    fun spawnSomeFoes() {
        with(dragon) {
            entities.add(this)
            map.addEntity(this)
        }

        repeat(6) {
            with(spawners.spawnFromAny(map)) {
                entities.add(this)
                map.addEntity(this)
            }
        }
    }

    /**
     * Link with the UI,
     * the monsters will play after the player has played if their move was accepted.
     */
    fun playTurn(playerAction: PlayerAction) {
        val accepted = player.act(playerAction, map)

        if(!accepted)
            return

        turnNumber++

        val died = mutableListOf<Int>()
        entities.forEachIndexed { idx, it ->
            if (it.currentHp <= 0){
                it.die(map)
                died.add(idx)
            }else
                it.takeTurn(map)
        }

        died.forEach { entities.removeAt(it) }

        if (turnNumber % 10 == 0) {
            with(spawners.spawnFromAny(map)) {
                entities.add(this)
                map.addEntity(this)
            }
        }
    }
}
