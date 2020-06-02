package com.gtomato.projects.backend.graphql

import com.expediagroup.graphql.SchemaGeneratorConfig
import com.expediagroup.graphql.TopLevelObject
import com.expediagroup.graphql.toSchema
import com.gtomato.projects.backend.service.WidgetService
import com.gtomato.projects.backend.service.WidgetUpdater
import graphql.schema.GraphQLSchema
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
class WidgetSchemaGeneratorConfig : InitializingBean{

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Resource
    private lateinit var graphQLSchema: GraphQLSchema

    @Bean
    fun getWidgetSchemaGenerator(): GraphQLSchema {

        val config = SchemaGeneratorConfig(supportedPackages = listOf("com.gtomato.projects.backend"))
        val queries = listOf(TopLevelObject(WidgetService()))
        val mutations = listOf(TopLevelObject(WidgetUpdater()))

        return toSchema(config, queries, mutations)
    }

    override fun afterPropertiesSet() {

        val description = graphQLSchema.typeMap.toString()

        log.info("Widget schema: $description")


    }
}