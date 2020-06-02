package com.gtomato.projects.backend.service

import com.expediagroup.graphql.spring.operations.Mutation
import com.expediagroup.graphql.spring.operations.Query
import com.gtomato.projects.backend.model.Widget
import com.gtomato.projects.backend.repository.WidgetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import javax.annotation.Resource

@Component
class WidgetUpdater: Mutation {

    @Resource
    private lateinit var widgetRepository: WidgetRepository

    suspend fun saveWidget(value: String): Widget {
        // Create and save a new widget, returns non-null
        val widget = Widget()
        widget.value = value
        return widgetRepository.save(widget).awaitFirst()
    }
}