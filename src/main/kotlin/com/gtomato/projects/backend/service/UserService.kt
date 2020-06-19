package com.gtomato.projects.backend.service

import com.gtomato.projects.backend.model.entity.User
import com.gtomato.projects.backend.repository.UserRepository
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

interface UserService {
    suspend fun getUserById (id: String): User

    fun getGraphQLUsersByIds (ids: List<String>): List<com.gtomato.projects.backend.graphql.User>
}

@Service
class UserServiceImpl: UserService {

    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Autowired
    private lateinit var userRepository: UserRepository

    override suspend fun getUserById(id: String): User {
        return withContext(context) {
            userRepository.findById(id) ?: throw RuntimeException("No user found for ID: $id")
        }
    }

    override fun getGraphQLUsersByIds(ids: List<String>): List<com.gtomato.projects.backend.graphql.User> {
        return ids.map {
            userRepository.findById(it)
                ?.let { user -> com.gtomato.projects.backend.graphql.User.fromEntity(user) }
                ?: throw RuntimeException("No user found for ID: $it")
        }
    }
}