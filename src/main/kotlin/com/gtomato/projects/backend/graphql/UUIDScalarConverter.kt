package com.gtomato.projects.backend.graphql

import java.util.*
import com.expediagroup.graphql.client.converter.ScalarConverter

class UUIDScalarConverter : ScalarConverter<UUID> {
    override fun toScalar(rawValue: String): UUID = UUID.fromString(rawValue)
    override fun toJson(value: UUID): String = value.toString()
}