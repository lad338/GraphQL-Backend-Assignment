package com.gtomato.projects.backend.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component


@Component
class DataSourceConfig {

    @Autowired
    private lateinit var environment: Environment

    @Bean
    fun getConnectionFactory(): ConnectionFactory {
        val connectionFactory: ConnectionFactory = ConnectionFactories.get(
                builder()
                .option(DRIVER, "postgresql")
                .option(HOST, this.environment.getProperty("postgres.host")?:"localhost")
                .option(PORT, this.environment.getProperty("postgres.port")?.toInt()?:5432) // optional, defaults to 5432
                .option(USER, this.environment.getProperty("postgres.username")?:"postgres")
                .option(PASSWORD, this.environment.getProperty("postgres.password")?:"1234")
                .option(DATABASE, this.environment.getProperty("postgres.database")?:"graphql") // optional
                .build()
        )
        return connectionFactory
    }

}