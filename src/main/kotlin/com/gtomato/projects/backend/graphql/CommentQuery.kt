//package com.gtomato.projects.backend.graphql
//
//import com.expediagroup.graphql.scalars.ID
//import com.expediagroup.graphql.spring.operations.Mutation
//import com.gtomato.projects.backend.repository.CommentRepository
//import com.gtomato.projects.backend.service.BookService
//import com.gtomato.projects.backend.service.UserService
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Service
//import java.util.*
//
//data class Comment(
//    val id: ID,
//    val content: String? = null,
//    val author: User? = null
//) {
//    companion object {
//        fun fromEntity (comment: com.gtomato.projects.backend.model.entity.Comment): Comment {
//            return Comment(
//                ID (comment.id.toString()),
//                comment.content,
//                comment.author?.let {User.fromEntity(it)}
//            )
//        }
//    }
//}
//
//@Service
//class CommentUpdater: Mutation {
//
//    companion object {
//        val context = CoroutineScope(Dispatchers.IO).coroutineContext
//    }
//
//    @Autowired
//    private lateinit var userService: UserService
//
//    @Autowired
//    private lateinit var bookService: BookService
//
//    suspend fun saveComment (content: String, authorId: String, bookId: String): Comment {
//        val author = userService.getUserById(authorId)
//        val book = bookService.getBookById(bookId)
//        val comment = com.gtomato.projects.backend.model.entity.Comment()
//        comment.id = UUID.randomUUID()
//        comment.content = content
//        comment.author = author
//        comment.book = book
//        bookService.addCommentForBook(book, comment)
//
//        return Comment.fromEntity(comment)
//    }
//
//}