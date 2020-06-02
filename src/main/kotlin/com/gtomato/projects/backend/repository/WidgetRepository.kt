package com.gtomato.projects.backend.repository

import com.gtomato.projects.backend.model.Widget
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface WidgetRepository : R2dbcRepository<Widget, Int>