package com.gtomato.projects.backend.service

import com.expediagroup.graphql.spring.operations.Query
import com.gtomato.projects.backend.model.entity.Widget
import com.gtomato.projects.backend.repository.WidgetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WidgetService: Query {
    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    suspend fun widgetById(id: Int): Widget? = withContext(context) {
        widgetRepository.findById(id).orElse(null)
    }

    suspend fun allWidgets(): List<Widget> = withContext(context) {
        widgetRepository.findAll()
    }

}