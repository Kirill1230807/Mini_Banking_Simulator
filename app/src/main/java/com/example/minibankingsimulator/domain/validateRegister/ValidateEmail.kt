package com.example.minibankingsimulator.domain.validateRegister

import android.util.Patterns
import javax.inject.Inject

class ValidateEmail @Inject constructor() {
    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(success = false, errorMessage = "Email can`t be empty")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(success = false, errorMessage = "That's not a valid email")
        }
        return ValidationResult(success = true)
    }
}

