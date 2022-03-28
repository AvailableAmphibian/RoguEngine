package entities

import androidx.compose.ui.window.ApplicationScope
import decorators.ItemDecorator
import main.main
import util.graph.tiles.TileMatrix
import util.player_action.PlayerAction
import kotlin.system.exitProcess

// EmojiDisplay : 👑
class Player(pos:Pair<Int, Int>): Entity<ItemDecorator>(10, 2, '@', pos, "\uD83D\uDC51") {
    fun act(action: PlayerAction, map:TileMatrix) = action.doIt(this, map)

    override fun takeTurn(map: TileMatrix) = true
    override fun die(map:TileMatrix) {
        super.die(map)
        println("You died !")
        exitProcess(0)
    }
}