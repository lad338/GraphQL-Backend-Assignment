package com.gtomato.projects.backend.repository

import com.gtomato.projects.backend.model.entity.Widget
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WidgetRepository : JpaRepository<Widget, Int> {

    @Query("SELECT w FROM Widget w WHERE w.id = :id")
    fun asyncFindById (id: Int): Widget?

    @Query("SELECT w FROM Widget w")
    fun getAll(): List<Widget>

}