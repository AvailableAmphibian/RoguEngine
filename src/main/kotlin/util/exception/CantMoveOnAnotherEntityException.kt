package util.exception

import entities.Entity
import util.helper.entityToString

class CantMoveOnAnotherEntityException(entity: Entity<*>, oEntity: Entity<*>):
    Exception("This entity (${entityToString(entity)}) can't move to the same position as another entity(${entityToString(oEntity)}).")