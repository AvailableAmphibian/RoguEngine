package entities

import decorators.ItemDecorator
import util.TileMatrix
import kotlin.random.Random

class Player(pos:Pair<Int, Int>): Entity<ItemDecorator>(10, 2, "@",pos) {
    private fun nextInt() = Random.Default.nextInt(3) - 1

    override fun move(map: TileMatrix) = Pair(nextInt(), nextInt())

}