package com.gtomato.projects.backend.repository

import com.gtomato.projects.backend.model.entity.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookRepository: JpaRepository<Book, UUID> {

    fun findByNameContainingAndAuthor_NameContaining (bookName: String, authorName: String, pageRequest: Pageable) : Page<Book>

    fun findByNameContainingOrAuthor_NameContaining (bookName: String, authorName: String, pageRequest: Pageable) : Page<Book>
}