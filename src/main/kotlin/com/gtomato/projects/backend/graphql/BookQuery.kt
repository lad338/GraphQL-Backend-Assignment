package com.gtomato.projects.backend.graphql

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import com.expediagroup.graphql.spring.operations.Query
import com.gtomato.projects.backend.repository.BookRepository
import com.gtomato.projects.backend.service.UserService
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import com.gtomato.projects.backend.model.entity.Book as BookEntity

//class BookBuilder(
//    val id: String,
//    val userService: UserService
//) {
//    fun build(): Book {
//
//    }
//}

class Book (
    val id: ID,
    val name: String? = null,
    val publishDate: String? = null,
    @GraphQLIgnore
    val authorId: String
    //    val numberOfComments: Int? = null,
    //    val comments: List<Comment>? = ArrayList()
) {
    lateinit var author: User
    companion object {
        fun fromEntity(book: BookEntity): Book {
            return Book (
                ID(book.id.toString()),
                book.name,
                book.publishDate?.toString(),
                book.authorId.toString()
            )
        }
    }
}

@Service
class BookQuery: Query {
    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var userService: UserService

    suspend fun allBooks(): List<Book> = withContext(context) {
        bookRepository.findAll().map {
            Book.fromEntity(it)
        }
    }

    suspend fun getBookById (id: String) : Book? = withContext(context) {
        bookRepository.findById(id)
            ?.let { Book.fromEntity(it) }
            ?: throw RuntimeException("No book found for ID: $id")
    }
//
//    suspend fun listBooks(
//        page: Int?,
//        size: Int?,
//        bookName: String?,
//        authorName: String?,
//        keyword: String?,
//        bookNameSort: Boolean?,             // True = ASC, False = DESC, Null = default order
//        publishDateSort: Boolean?           // True = ASC, False = DESC, Null = default order
//    ): List<Book> = withContext(context) {
//        val pageRequest = PageRequest.of(page ?: 0, size ?: 5)
//
//        val pageResults = keyword ?.let {
//                bookRepository.findByNameContainingOrAuthor_NameContaining(it, it, pageRequest)
//            }
//            ?: bookRepository.findByNameContainingAndAuthor_NameContaining(bookName ?: "", authorName ?: "", pageRequest)
//
//        pageResults.content.map { Book.fromEntity(it) }
//    }
}

data class BookMutator (
    val name: String,
    val publishDate: String? = null,
    val authorId: String
)


@Service
class BookUpdater: Mutation {

    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var userService: UserService

    suspend fun saveBook (book: BookMutator): Book = withContext(context) {
        val author = userService.getUserById(book.authorId)
        transaction {
            val newBook = BookEntity.new {
                this.name = book.name
                this.publishDate = book.publishDate?.let { DateTime.parse(it) }
                this.authorId = author.id
            }

            Book.fromEntity(newBook)
        }
    }

}

@Component("UserDataFetcher")
class UserDataFetcher : DataFetcher<CompletableFuture<User>>, BeanFactoryAware {
    private lateinit var beanFactory: BeanFactory

    override fun setBeanFactory(beanFactory: BeanFactory) {
        this.beanFactory = beanFactory
    }

    override fun get(environment: DataFetchingEnvironment): CompletableFuture<User> {
        val authorId = environment.getSource<Book>().authorId
        return environment
            .getDataLoader<String, User>("userLoader")
            .load(authorId)
    }
}

