package com.example.minibankingsimulator.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minibankingsimulator.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginFormState(
    val email: String = "",
    val password: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _formState = MutableStateFlow(LoginFormState())
    val formState = _formState.asStateFlow()

    private val _uiState = MutableStateFlow<LoginScreenState>(LoginScreenState.Initial)
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                _formState.value = _formState.value.copy(email = event.email)
            }
            is LoginEvent.OnPasswordChange -> {
                _formState.value = _formState.value.copy(password = event.password)
            }
            LoginEvent.OnLoginClick -> {
                login()
            }
        }
    }

    private fun login() {
        val email = _formState.value.email
        val password = _formState.value.password

        viewModelScope.launch {
            _uiState.value = LoginScreenState.Loading

            val result = signInUseCase(email, password)

            result.onSuccess {
                _uiState.value = LoginScreenState.Success("login success")
            }.onFailure {
                _uiState.value = LoginScreenState.Error("login failed")
            }
        }
    }
}
