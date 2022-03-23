package entities

import decorators.ItemDecorator
import util.exception.CantMoveOnAnotherEntityException
import util.exception.CantMoveToTheSamePositionException
import util.helper.TileMatrix
import kotlin.random.Random

class Player(pos:Pair<Int, Int>): Entity<ItemDecorator>(10, 2, '@', "\uD83D\uDC51", pos) {
    private fun nextInt() = Random.Default.nextInt(3) - 1
}