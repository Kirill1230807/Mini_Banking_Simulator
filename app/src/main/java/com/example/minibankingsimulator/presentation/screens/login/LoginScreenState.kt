package com.example.minibankingsimulator.presentation.screens.login

sealed interface LoginScreenState {
    data object Initial : LoginScreenState
    data object Loading : LoginScreenState
    data class Success(val message: String) : LoginScreenState
    data class Error(val message: String) : LoginScreenState
}