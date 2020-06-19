package com.gtomato.projects.backend.config

import com.gtomato.projects.backend.model.entity.Books
import com.gtomato.projects.backend.model.entity.Users
import com.gtomato.projects.backend.model.entity.Widgets
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class DatabaseConfig: InitializingBean {

    @Autowired
    private lateinit var databaseConnection: Database

    @Bean
    fun getDatabaseConnection(): Database {
        val connection = Database.connect(
            "jdbc:postgresql://localhost:5432/graphql",
            "org.postgresql.Driver",
            "postgres",
            "1234"
        )
        return connection
    }

    override fun afterPropertiesSet() {
        databaseConnection
        transaction {
            SchemaUtils.create(
                Widgets,
                Users,
                Books
            )
        }
    }

}