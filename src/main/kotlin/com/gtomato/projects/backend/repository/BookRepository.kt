package com.gtomato.projects.backend.repository

import com.gtomato.projects.backend.model.entity.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookRepository: JpaRepository<Book, UUID> {
}