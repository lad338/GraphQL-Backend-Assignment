package com.gtomato.projects.backend.graphql

import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import com.expediagroup.graphql.spring.operations.Query
import com.gtomato.projects.backend.repository.UserRepository
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import com.gtomato.projects.backend.model.entity.User as UserEntity

data class User(
    val id: ID,
    val name: String? = null,
    val avatar: String? = null
) {
    companion object {
        fun fromEntity (user: UserEntity): User {
            return User(
                ID(user.id.toString()),
                user.name,
                user.avatar
            )
        }
    }
}

@Service
class UserQuery: Query {

    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun allUsers(): List<User> = withContext(context) {
        userRepository.findAll().map { User.fromEntity(it) }
    }
}

@Service
class UserUpdater: Mutation {

    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun saveUser (name: String): User = withContext(context) {
        transaction {
            val user = UserEntity.new {
                this.name = name
            }
            User.fromEntity(user)
        }
    }
}
