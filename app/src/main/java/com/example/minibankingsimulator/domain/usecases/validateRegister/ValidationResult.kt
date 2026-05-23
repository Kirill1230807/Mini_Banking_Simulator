package com.example.minibankingsimulator.domain.usecases.validateRegister

data class ValidationResult(
    val success: Boolean,
    val errorMessage: String? = null
)
