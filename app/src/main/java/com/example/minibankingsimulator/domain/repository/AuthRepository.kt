package com.example.minibankingsimulator.domain.repository

import com.example.minibankingsimulator.domain.model.Account
import com.example.minibankingsimulator.domain.model.User

interface AuthRepository {
    suspend fun register(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
        email: String
    ): Result<User>

    suspend fun login(email: String, password: String): Result<User>
    suspend fun logout()
    suspend fun getCurrentUser(): User?
}