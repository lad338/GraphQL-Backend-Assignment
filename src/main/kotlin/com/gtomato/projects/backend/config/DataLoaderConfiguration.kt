package com.gtomato.projects.backend.config

import com.expediagroup.graphql.spring.execution.DataLoaderRegistryFactory
import com.gtomato.projects.backend.graphql.User
import com.gtomato.projects.backend.service.UserService
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.CompletableFuture

@Configuration
class DataLoaderConfiguration(private val userService: UserService) {
    @Bean
    fun dataLoaderRegistryFactory(): DataLoaderRegistryFactory {

        return object : DataLoaderRegistryFactory {
            override fun generate(): DataLoaderRegistry {

                val registry = DataLoaderRegistry()
                val userLoader = DataLoader<String, User> { ids ->
                    CompletableFuture.supplyAsync { userService.getGraphQLUsersByIds(ids) }
                }
                registry.register("userLoader", userLoader)
                return registry
            }
        }
    }
}