package com.gtomato.projects.backend.model.entity

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDTable
import java.util.*

object Users: UUIDTable() {
    val name = Users.varchar("name", 50)
    val avatar = Users.varchar("avatar", 50).nullable()
}

class User(id: EntityID<UUID>): Entity<UUID> (id) {
    companion object: EntityClass<UUID, User>(Users)
    var name by Users.name
    var avatar by Users.avatar
}

