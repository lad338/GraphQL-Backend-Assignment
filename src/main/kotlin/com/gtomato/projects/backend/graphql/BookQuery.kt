package com.gtomato.projects.backend.graphql

import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import com.gtomato.projects.backend.repository.BookRepository
import com.gtomato.projects.backend.service.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
import java.util.*

data class Book (
    val id: ID,
    val name: String? = null,
    val author: User? = null,
    val publishDate: String? = null
) {
    companion object {
        fun fromEntity (book: com.gtomato.projects.backend.model.entity.Book): Book {
            return Book(
                ID(book.id.toString()),
                book.name,
                book.author?.let { User.fromEntity(it) },
                book.publishDate?.toString()
            )
        }
    }
}




@Service
class BookUpdater: Mutation {

    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var userService: UserService

    suspend fun saveBook (name: String, authorId: String?, publishDate: String?): Book {
        val book = com.gtomato.projects.backend.model.entity.Book()
        val author = authorId?.let{ withContext(context) {userService.getUserById(it) } }
        book.author = author
        book.name = name
        book.publishDate = publishDate?.let {Date.valueOf(it)}
        book.id = UUID.randomUUID()
        return Book.fromEntity(bookRepository.save(book))

    }

}


