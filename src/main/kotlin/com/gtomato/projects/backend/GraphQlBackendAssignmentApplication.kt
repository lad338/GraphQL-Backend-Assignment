package com.gtomato.projects.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
@EnableR2dbcRepositories(value = ["com.gtomato.projects.backend.repository"])
class GraphQlBackendAssignmentApplication

fun main(args: Array<String>) {
	runApplication<GraphQlBackendAssignmentApplication>(*args)
}
