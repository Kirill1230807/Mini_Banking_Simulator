package com.example.minibankingsimulator.domain.validateRegister

import javax.inject.Inject

class ValidatePhoneNumber @Inject constructor() {
    fun execute(phoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                success = false,
                errorMessage = "Password can`t be empty"
            )
        }
        if (phoneNumber.length < 10) {
            return ValidationResult(
                success = false,
                errorMessage = "Phone number must have 10 numbers"
            )
        }
        return ValidationResult(true)
    }
}