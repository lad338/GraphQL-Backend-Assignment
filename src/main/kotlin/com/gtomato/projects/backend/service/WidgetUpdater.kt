package com.gtomato.projects.backend.service

import com.expediagroup.graphql.spring.operations.Mutation
import com.gtomato.projects.backend.model.entity.Widget
import com.gtomato.projects.backend.repository.WidgetRepository
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
class WidgetUpdater: Mutation {

    @Resource
    private lateinit var widgetRepository: WidgetRepository

    suspend fun saveWidget(value: String): Widget {
        // Create and save a new widget, returns non-null
        val widget = Widget()
        widget.value = value
        return widgetRepository.save(widget)
    }
}