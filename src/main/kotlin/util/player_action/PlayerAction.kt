package util.player_action

import entities.Player
import util.graph.tiles.TileMatrix

/**
 * Strategy used to handle the different player actions.
 */
fun interface PlayerAction {
    /**
     * The action defined.
     *
     * @param player the player doing the action
     * @param map    the map the player is on
     * @return Should return false if the action couldn't be used, otherwise true.
     */
    fun doIt(player: Player, map:TileMatrix): Boolean

    fun moveOrAttackAt(player:Player, map:TileMatrix, addToX:Int, addToY: Int): Boolean{
        val (x,y) = player.position
        val newPos = Pair(x+addToX, y+addToY)
        if (!player.canMove(newPos, map)) {
            map[newPos].nEntState.value?.let {
                player.attack(it)
                return true
            }

            return false
        }
        player.move(newPos, map)
        return true
    }
}

/**
 * Instance of <code>PlayerMove</code> used to make the player go in the tile above.
 */
object PlayerActUp: PlayerAction {
    override fun doIt(player: Player, map:TileMatrix) = moveOrAttackAt(player, map, -1, 0)
}

/**
 * Instance of <code>PlayerMove</code> used to make the player go in the tile below.
 */
object PlayerActDown: PlayerAction {
    override fun doIt(player: Player, map:TileMatrix) = moveOrAttackAt(player, map, 1, 0)
}

/**
 * Instance of <code>PlayerMove</code> used to make the player go in the tile on the left.
 */
object PlayerActLeft: PlayerAction {
    override fun doIt(player: Player, map:TileMatrix) = moveOrAttackAt(player, map, 0, -1)
}

/**
 * Instance of <code>PlayerMove</code> used to make the player go in the tile on the right.
 */
object PlayerActRight: PlayerAction {
    override fun doIt(player: Player, map:TileMatrix) = moveOrAttackAt(player, map, 0, 1)
}
