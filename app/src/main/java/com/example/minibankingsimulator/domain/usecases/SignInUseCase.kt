package com.example.minibankingsimulator.domain.usecases

import com.example.minibankingsimulator.domain.model.User
import com.example.minibankingsimulator.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Email and password cannot be empty"))
        }
        return repository.login(email, password)
    }
}
