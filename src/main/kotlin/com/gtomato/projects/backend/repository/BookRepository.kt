package com.gtomato.projects.backend.repository

import com.gtomato.projects.backend.filter.Pagination
import com.gtomato.projects.backend.graphql.BookFilter
import com.gtomato.projects.backend.model.entity.Book
import com.gtomato.projects.backend.model.entity.Books
import com.gtomato.projects.backend.model.entity.User
import com.gtomato.projects.backend.model.entity.Users
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

interface BookRepository{
    fun findById (id: String): Book?

    fun findAll(): List<Book>

    fun search(bookFilter: BookFilter, page: Pagination): List<Book>
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

    override fun search(bookFilter: BookFilter, page: Pagination): List<Book> {
        return transaction {
                Book.wrapRows(
                    (Books innerJoin Users)
                        .slice(Books.columns)
                        .select(Op.TRUE)
                        .limit(page.limit, page.offset)
                )
            }.toList()
    }
}
