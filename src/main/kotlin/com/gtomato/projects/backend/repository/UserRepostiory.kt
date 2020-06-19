package com.gtomato.projects.backend.repository

import com.gtomato.projects.backend.model.entity.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

interface UserRepository {

    fun findById (id: String): User?

    fun findAll(): List<User>
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
}
