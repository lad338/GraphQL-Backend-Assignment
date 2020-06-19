package com.gtomato.projects.backend.model.entity

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDTable
import java.util.*

object Books: UUIDTable() {
    val name = Books.varchar("name", 50)
    val publishDate = Books.datetime("publishDate").nullable()
    val authorId = Books.reference("authorId", Users)
}


class Book (id: EntityID<UUID>): Entity<UUID>(id) {
    companion object: EntityClass<UUID, Book> (Books)
    var name by Books.name
    var publishDate by Books.publishDate
    var authorId by Books.authorId
}