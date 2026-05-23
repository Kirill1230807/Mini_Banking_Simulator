package com.example.minibankingsimulator.domain.usecases

import com.example.minibankingsimulator.domain.model.User
import com.example.minibankingsimulator.domain.repository.AuthRepository
import com.example.minibankingsimulator.domain.usecases.validateRegister.ValidateEmail
import com.example.minibankingsimulator.domain.usecases.validateRegister.ValidatePassword
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        val emailResult = validateEmail.execute(email)
        val passwordResult = validatePassword.execute(password)

        if (!emailResult.success) {
            return Result.failure(Exception(emailResult.errorMessage))
        }
        if (!passwordResult.success) {
            return Result.failure(Exception(passwordResult.errorMessage))
        }
        return repository.login(email, password)
    }
}
