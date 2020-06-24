package com.gtomato.projects.backend.graphql

import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import com.expediagroup.graphql.spring.operations.Query
import com.gtomato.projects.backend.filter.GraphQLStringFilter
import com.gtomato.projects.backend.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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

data class UserFilter (
    val id: GraphQLStringFilter?= null,
    val name: GraphQLStringFilter?= null
)



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

    suspend fun searchUsers(userFilter: UserFilter) = withContext(context) {
        userRepository.search(userFilter).map { User.fromEntity(it) }
    }
}

data class UserMutator (
    val name: String,
    val avatar: String? = null
)

@Service
class UserUpdater: Mutation {

    companion object {
        val context = CoroutineScope(Dispatchers.IO).coroutineContext
    }

    @Autowired
    private lateinit var userRepository: UserRepository

    suspend fun saveUser (user: UserMutator): User = withContext(context) {
        transaction {
            val newUser = UserEntity.new {
                this.name = user.name
            }
            User.fromEntity(newUser)
        }
    }
}
