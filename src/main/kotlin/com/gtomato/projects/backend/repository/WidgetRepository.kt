package com.gtomato.projects.backend.repository

import com.gtomato.projects.backend.model.entity.Widget
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

interface WidgetRepository  {

    fun findById (id: String): Widget?

    fun getAll(): List<Widget>

}

@Repository
class WidgetRepositoryImpl: WidgetRepository {


    override fun findById(id: String): Widget? {
        return transaction {
            Widget.findById(UUID.fromString(id))
        }
    }

    override fun getAll(): List<Widget> {
        return transaction {
            Widget.all().toList()
        }
    }

}