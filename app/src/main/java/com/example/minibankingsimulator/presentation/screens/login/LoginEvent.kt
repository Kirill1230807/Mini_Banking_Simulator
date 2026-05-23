package com.example.minibankingsimulator.presentation.screens.login

sealed interface LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent
    data class OnPasswordChange(val password: String) : LoginEvent
    data object OnLoginClick : LoginEvent
}