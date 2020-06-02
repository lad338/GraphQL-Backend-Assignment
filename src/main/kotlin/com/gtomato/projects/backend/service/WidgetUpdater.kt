package com.gtomato.projects.backend.service

import com.expediagroup.graphql.spring.operations.Query
import com.gtomato.projects.backend.model.Widget
import com.gtomato.projects.backend.repository.WidgetRepository
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import javax.annotation.Resource

@Component
class WidgetUpdater: Query {

    @Resource
    private lateinit var widgetRepository: WidgetRepository

    fun saveWidget(value: String): Widget {
        // Create and save a new widget, returns non-null
        val widget = Widget()
        widget.value = value
        return widgetRepository.save(widget).blockOptional().orElseThrow()
    }
}