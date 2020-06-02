package com.gtomato.projects.backend.service

import com.expediagroup.graphql.spring.operations.Query
import com.gtomato.projects.backend.model.Widget
import com.gtomato.projects.backend.repository.WidgetRepository
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class WidgetService: Query {

    @Resource
    private lateinit var widgetRepository: WidgetRepository

    fun widgetById(id: Int): Widget? {
        return widgetRepository.findById(id).block()
    }

//    @Deprecated("Use widgetById")
//    fun widgetByValue(value: String): Widget? {
//        // grabs widget from a deprecated data source, might return null
//    }
}