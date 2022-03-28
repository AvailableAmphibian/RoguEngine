package util.graph.tiles

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import entities.Entity
import util.graph.SearchNode

open class Tile(nodeAuthorized:Boolean = true): SearchNode(nodeAuthorized) {
    /**
     * Wrapping the Entity in a mutableState to force the update of a Tile.
     */
    val nEntState:MutableState<Entity<*>?> = mutableStateOf(null)
    open val charDisplay get() = nEntState.value?.charDisplay ?: '.'
    open val emojiDisplay get() = nEntState.value?.emojiDisplay ?: "▪"

    override fun toString() = "Entity: ${nEntState.value?.charDisplay ?: "null"}; pos: ${this.xy}; display: $charDisplay"
}