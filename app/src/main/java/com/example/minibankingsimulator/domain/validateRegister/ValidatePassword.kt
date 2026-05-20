package com.example.minibankingsimulator.domain.validateRegister

import javax.inject.Inject

class ValidatePassword @Inject constructor() {
    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                success = false,
                errorMessage = "The password can't be blank"
            )
        }
        if (password.length < 8) {
            return ValidationResult(
                success = false,
                errorMessage = "The password needs to consist of at least 8 characters"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                success = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }
        return ValidationResult(success = true)
    }
}