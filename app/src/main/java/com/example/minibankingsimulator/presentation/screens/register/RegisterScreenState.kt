package com.example.minibankingsimulator.presentation.screens.register

sealed interface RegisterScreenState {
    data object Initial : RegisterScreenState
    data object Loading : RegisterScreenState
    data class Success(val message: String) : RegisterScreenState
    data class Error(val message: String) : RegisterScreenState
}