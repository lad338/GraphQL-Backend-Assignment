package com.gtomato.projects.backend.service

import com.expediagroup.graphql.spring.operations.Query
import com.gtomato.projects.backend.model.Widget
import com.gtomato.projects.backend.repository.WidgetRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WidgetService: Query {

    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    suspend fun widgetById(id: Int): Widget? {
        return widgetRepository.findById(id).awaitFirst()
    }

    suspend fun allWidgets(): List<Widget> {
        return widgetRepository.findAll().asFlow().toList()
    }

}