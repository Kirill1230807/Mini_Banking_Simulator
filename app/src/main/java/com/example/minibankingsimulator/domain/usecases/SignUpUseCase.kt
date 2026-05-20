package com.example.minibankingsimulator.domain.usecases

import com.example.minibankingsimulator.domain.model.User
import com.example.minibankingsimulator.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
        email: String
    ): Result<User> {
        // Тут можна додати валідацію, наприклад:
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("All fields are required"))
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(Exception("Invalid email format"))
        }

        return repository.register(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            password = password,
            email = email
        )
    }
}
