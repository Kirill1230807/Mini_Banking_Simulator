package com.example.minibankingsimulator.domain.validateRegister

data class ValidationResult(
    val success: Boolean,
    val errorMessage: String? = null
)
