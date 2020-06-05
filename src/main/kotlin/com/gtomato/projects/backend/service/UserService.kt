package com.gtomato.projects.backend.service

import com.gtomato.projects.backend.model.entity.Book
import com.gtomato.projects.backend.model.entity.User
import com.gtomato.projects.backend.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

interface UserService {
    suspend fun getUserById (id: String): User
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
            userRepository.findById(UUID.fromString(id))
                .orElseThrow {
                    RuntimeException("No user found for ID: $id")
                }
        }
    }
}