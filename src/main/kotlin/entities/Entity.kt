package entities

import decorators.BaseDecorator
import util.lambda_strategies.entity_related.MovingStrategy
import java.util.*

abstract class Entity<T:BaseDecorator?>(val baseHp:Int, val baseAtk:Int, val displayString: String,var position:Pair<Int, Int>): MovingStrategy {
    var currentHp = baseHp
    val decorators = ArrayList<T>()
}