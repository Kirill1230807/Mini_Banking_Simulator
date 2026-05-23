package com.example.minibankingsimulator.domain.usecases

import com.example.minibankingsimulator.domain.model.User
import com.example.minibankingsimulator.domain.repository.AuthRepository
import com.example.minibankingsimulator.domain.usecases.validateRegister.ValidateEmail
import com.example.minibankingsimulator.domain.usecases.validateRegister.ValidatePassword
import com.example.minibankingsimulator.domain.usecases.validateRegister.ValidatePhoneNumber
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validatePhoneNumber: ValidatePhoneNumber
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
        email: String
    ): Result<User> {
        val emailResult = validateEmail.execute(email)
        val passwordResult = validatePassword.execute(password)
        val phoneResult = validatePhoneNumber.execute(phoneNumber)

        if (!emailResult.success) {
            return Result.failure(Exception(emailResult.errorMessage))
        }
        if (!passwordResult.success) {
            return Result.failure(Exception(passwordResult.errorMessage))
        }
        if (!phoneResult.success) {
            return Result.failure(Exception(phoneResult.errorMessage))
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
