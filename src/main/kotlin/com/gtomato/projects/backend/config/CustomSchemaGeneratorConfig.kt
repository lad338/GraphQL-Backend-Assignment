package com.gtomato.projects.backend.config

import graphql.scalars.ExtendedScalars

import graphql.schema.GraphQLScalarType
import org.springframework.context.annotation.Bean


class CustomSchemaGeneratorConfig {
    @Bean
    fun jsonType(): GraphQLScalarType? {
        return ExtendedScalars.DateTime
    }
}