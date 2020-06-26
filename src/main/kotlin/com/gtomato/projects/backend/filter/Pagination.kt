package com.gtomato.projects.backend.filter

data class Pagination (
    val limit: Int = 10,
    val offset: Int = 0
)