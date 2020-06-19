package com.gtomato.projects.backend.graphql

import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import com.expediagroup.graphql.spring.operations.Query
import com.gtomato.projects.backend.repository.WidgetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.annotation.Resource
import com.gtomato.projects.backend.model.entity.Widget as WidgetEntity

data class Widget(
    val id: ID,
    val value: String? = null
) {
    companion object {
        fun fromEntity (widget: WidgetEntity): Widget {
            return Widget(
                ID(widget.id.toString()),
                widget.value
            )
        }
    }
}


@Service
class WidgetQuery: Query {
    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    suspend fun widgetById(id: String): Widget? = withContext(context) {
        widgetRepository
            .findById(id)
            ?.let { Widget.fromEntity(it) }
    }

    suspend fun allWidgets(): List<Widget> = withContext(context) {
        widgetRepository
            .findAll()
            .map {
                Widget.fromEntity(it)
            }
    }

}

@Component
class WidgetUpdater: Mutation {

    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Resource
    private lateinit var widgetRepository: WidgetRepository

    suspend fun saveWidget(value: String): Widget = withContext(context) {
        transaction {
            val widget = WidgetEntity.new {
                this.value = value
            }
            Widget.fromEntity(widget)
        }
    }
}
