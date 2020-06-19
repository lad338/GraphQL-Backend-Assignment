package com.gtomato.projects.backend.repository

import com.gtomato.projects.backend.model.entity.Book
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

interface BookRepository{
    fun findById (id: String): Book?

    fun findAll(): List<Book>
}

@Repository
class BookRepositoryImpl: BookRepository {
    override fun findById(id: String): Book? {
        return transaction {
            Book.findById(UUID.fromString(id))
        }
    }

    override fun findAll(): List<Book> {
        return transaction {
            Book.all().toList()
        }
    }
}
