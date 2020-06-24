package com.gtomato.projects.backend.filter

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notLike
import org.jetbrains.exposed.sql.SqlExpressionBuilder.regexp
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or

data class GraphQLStringFilter (
    val eq: String? = null,
    val neq: String? = null,
    val like: String? = null,
    val notLike: String? = null,
    val regex: String? = null,
    val and: List<GraphQLStringFilter>? = null,
    val or: List<GraphQLStringFilter>? = null
)

fun applyStringFilterToColumn (column: Column<String>, filter: GraphQLStringFilter): Op<Boolean> {
    filter.eq?.let {
        return column eq it
    }
    filter.neq?.let {
        return column neq it
    }
    filter.like?.let {
        return column like it
    }
    filter.notLike?.let {
        return column notLike it
    }
    filter.regex?.let {
        return column regexp it
    }
    filter.and?.let {
        return it.map { filter ->
            applyStringFilterToColumn(column, filter)
        }
            .reduce(Op<Boolean>::and)
    }
    filter.or?.let {
        return it.map {filter ->
            applyStringFilterToColumn(column, filter)
        }
            .reduce(Op<Boolean>::or)
    }
    return Op.TRUE
}



