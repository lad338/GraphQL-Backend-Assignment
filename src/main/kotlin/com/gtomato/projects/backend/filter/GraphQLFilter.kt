package com.gtomato.projects.backend.filter

import com.gtomato.projects.backend.exception.FilterTypeNotSupportedException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notLike
import org.jetbrains.exposed.sql.SqlExpressionBuilder.regexp
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greater
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.joda.time.DateTime

sealed class Filter<FilterType, ColumnType> (
    val eq: FilterType? = null,
    val neq: FilterType? = null,
    open val and: List<Filter<FilterType, ColumnType>>? = null,
    open val or: List<Filter<FilterType, ColumnType>>? = null
) : FilterAdapter<ColumnType> {
    override fun toAnd(): List<FilterAdapter<ColumnType>>? {
        return and
    }

    override fun toOr(): List<FilterAdapter<ColumnType>>? {
        return or
    }

    override fun toEq(): ColumnType? {
        throw FilterTypeNotSupportedException("EQUAL filter is not supported")
    }

    override fun toNeq(): ColumnType? {
        throw FilterTypeNotSupportedException("NOT EQUAL filter is not supported")
    }
}

sealed class ComparableFilter<FilterType, ColumnType: Comparable<ColumnType>> (
    open val gt: FilterType? = null,
    open val lt: FilterType? = null,
    open val gte: FilterType? = null,
    open val lte: FilterType? = null,
    override val and: List<ComparableFilter<FilterType, ColumnType>>? = null,
    override val or: List<ComparableFilter<FilterType, ColumnType>>? = null
) : Filter<FilterType, ColumnType> (), ComparableFilterAdapter<ColumnType> {

    override fun toGt(): ColumnType? {
        throw FilterTypeNotSupportedException("GREATER THAN filter is not supported")
    }

    override fun toGte(): ColumnType? {
        throw FilterTypeNotSupportedException("GREATER THAN OR EQUAL filter is not supported")
    }

    override fun toLt(): ColumnType? {
        throw FilterTypeNotSupportedException("LESS THAN filter is not supported")
    }

    override fun toLte(): ColumnType? {
        throw FilterTypeNotSupportedException("LESS THAN OR EQUAL filter is not supported")
    }
}

class DateTimeFilter : ComparableFilter<String, DateTime> () {
    override fun toEq(): DateTime? {
        return eq?.let { DateTime.parse(it) }
    }

    override fun toNeq(): DateTime? {
        return neq?.let { DateTime.parse(it) }
    }

    override fun toGt(): DateTime? {
        return gt?.let { DateTime.parse(it) }
    }

    override fun toGte(): DateTime? {
        return gte?.let { DateTime.parse(it) }
    }

    override fun toLt(): DateTime? {
        return lt?.let { DateTime.parse(it) }
    }

    override fun toLte(): DateTime? {
        return lte?.let { DateTime.parse(it) }
    }
}

data class StringFilter (
    val like: String? = null,
    val notLike: String? = null,
    val regex: String? = null,
    override val and: List<StringFilter>? = null,
    override val or: List<StringFilter>? = null
) : ComparableFilter<String, String> (), StringFilterAdapter {
    override fun toEq(): String? {
        return eq
    }

    override fun toNeq(): String? {
        return neq
    }

    override fun toGt(): String? {
        return gt
    }

    override fun toGte(): String? {
        return gte
    }

    override fun toLt(): String? {
        return lt
    }

    override fun toLte(): String? {
        return lte
    }

    override fun toLike(): String? {
        return like
    }

    override fun toNotLike(): String? {
        return notLike
    }

    override fun toRegex(): String? {
        return regex
    }
}

interface FilterAdapter<T> {
    fun toAnd(): List<FilterAdapter<T>>?
    fun toOr(): List<FilterAdapter<T>>?

    fun toEq(): T?
    fun toNeq(): T?
}

interface ComparableFilterAdapter<T: Comparable<T>>: FilterAdapter<T> {
    fun toGt(): T?
    fun toGte(): T?
    fun toLt(): T?
    fun toLte(): T?
}

interface StringFilterAdapter: ComparableFilterAdapter<String> {
    fun toLike(): String?
    fun toNotLike(): String?
    fun toRegex(): String?
}

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

fun applyStringFilterToColumn (column: Column<String>, filter: StringFilterAdapter): Op<Boolean> {
    filter.toLike()?.let {
        return column like it
    }
    filter.toNotLike()?.let {
        return column notLike it
    }

    filter.toRegex()?.let {
        return column regexp it
    }
    return applyComparableFilterToColumn(column, filter)
}

fun <ColumnType: Comparable<ColumnType>> applyComparableFilterToColumn (column: Column<ColumnType>, filter: ComparableFilterAdapter<ColumnType>): Op<Boolean> {
    filter.toGt()?.let {
        return column greater it
    }
    filter.toGte()?.let {
        return column greaterEq it
    }
    filter.toLt()?.let {
        return column less it
    }
    filter.toLte()?.let {
        return column lessEq it
    }
    return applyFilterToColumn(column, filter)
}

fun <ColumnType> applyFilterToColumn (column: Column<ColumnType>, filter: FilterAdapter<ColumnType>): Op<Boolean> {
    filter.toEq()?.let {
        return column eq it
    }
    filter.toNeq()?.let {
        return column neq it
    }
    filter.toAnd()?.let {
        return it.map {filter ->
            applyFilterToColumn(column, filter)
        }.reduce(Op<Boolean>:: or)
    }
    filter.toOr()?.let {
        return it.map {filter ->
            applyFilterToColumn(column, filter)
        }.reduce(Op<Boolean>:: or)
    }
    return Op.TRUE
}


