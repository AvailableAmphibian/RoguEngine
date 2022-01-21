package util.helper

import entities.Entity

fun entityToString(entity: Entity<*>) = entity.javaClass.name +"@"+Integer.toHexString(entity.hashCode())
