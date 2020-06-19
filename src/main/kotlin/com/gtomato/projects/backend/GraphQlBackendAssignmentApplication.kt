package com.gtomato.projects.backend

import com.fasterxml.jackson.databind.ObjectMapper
import com.gtomato.projects.backend.config.CustomDataFetcherFactoryProvider
import com.gtomato.projects.backend.config.SpringDataFetcherFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class GraphQlBackendAssignmentApplication {

	@Bean
	fun dataFetcherFactoryProvider(springDataFetcherFactory: SpringDataFetcherFactory, objectMapper: ObjectMapper) =
		CustomDataFetcherFactoryProvider(springDataFetcherFactory, objectMapper)

}

fun main(args: Array<String>) {
	runApplication<GraphQlBackendAssignmentApplication>(*args)
}

