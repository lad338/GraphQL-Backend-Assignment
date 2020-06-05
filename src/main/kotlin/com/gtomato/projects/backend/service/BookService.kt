package com.gtomato.projects.backend.service

import com.gtomato.projects.backend.model.entity.Book
import com.gtomato.projects.backend.model.entity.Comment
import com.gtomato.projects.backend.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

interface BookService {
    suspend fun getBookById (id: String): Book

    suspend fun addCommentForBook (book: Book, comment: Comment): Book
}

@Service
class BookServiceImpl: BookService {

    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Autowired
    private lateinit var bookRepository: BookRepository

    override suspend fun getBookById(id: String): Book {
        return withContext(context) {
            bookRepository.findById(UUID.fromString(id))
                .orElseThrow {
                    RuntimeException("No book found for ID: $id")
                }
        }
    }

    override suspend fun addCommentForBook(book: Book, comment: Comment): Book {
        val comments = book.comments.toMutableList()
        comments.add(comment)
        book.comments = comments
        return bookRepository.save(book)
    }
}