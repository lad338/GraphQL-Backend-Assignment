package com.gtomato.projects.backend.model.entity

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDTable
import java.util.*

object Widgets: UUIDTable() {
    val value = varchar("value", 50)
}

class Widget(id: EntityID<UUID>): Entity<UUID> (id) {
    companion object: EntityClass<UUID, Widget> (Widgets)

    var value by Widgets.value

}