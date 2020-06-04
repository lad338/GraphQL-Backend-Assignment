package com.gtomato.projects.backend.service

import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import com.expediagroup.graphql.spring.operations.Query
import com.gtomato.projects.backend.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

data class User(
    val id: ID,
    val name: String? = null,
    val avatar: String? = null
) {
    companion object {
        fun fromEntity (user: com.gtomato.projects.backend.model.entity.User): User {
            return User(ID(user.id.toString()), user.name, user.avatar)
        }
    }
}

@Service
class UserService: Query {

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

    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun saveUser (name: String): User {
        val user = com.gtomato.projects.backend.model.entity.User()
        user.name = name
        user.id = UUID.randomUUID()
        val savedUser = userRepository.save(user)
        return User.fromEntity(savedUser)
    }
}

