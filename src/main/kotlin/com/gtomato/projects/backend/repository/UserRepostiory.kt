package com.gtomato.projects.backend.repository

import com.gtomato.projects.backend.filter.applyStringFilterToColumn
import com.gtomato.projects.backend.graphql.UserFilter
import com.gtomato.projects.backend.model.entity.User
import com.gtomato.projects.backend.model.entity.Users
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

interface UserRepository {

    fun findById (id: String): User?

    fun findAll(): List<User>

    fun search(userFilter: UserFilter): List<User>
}


@Repository
class UserRepositoryImpl: UserRepository {
    override fun findById(id: String): User? {
        return transaction {
            User.findById(UUID.fromString(id))
        }
    }

    override fun findAll(): List<User> {
        return transaction {
            User.all().toList()
        }
    }

    override fun search(userFilter: UserFilter): List<User> {

        return transaction {
            User.find {
                userFilter.name
                    ?.let {
                        applyStringFilterToColumn(Users.name, it)
                    }
                    ?: Op.TRUE
                }
                .toList()
        }
    }
}
