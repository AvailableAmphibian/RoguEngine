package util.exception

import entities.Entity
import util.helper.entityToString


class CantMoveToTheSamePositionException(entity: Entity<*>, position:Pair<Int, Int>):
    Exception("This entity (${entityToString(entity)}) can't move to the same position (${entity.position} to $position).") {

}