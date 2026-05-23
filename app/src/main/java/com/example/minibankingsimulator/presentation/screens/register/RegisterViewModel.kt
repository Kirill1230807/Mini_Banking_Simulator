package com.example.minibankingsimulator.presentation.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minibankingsimulator.domain.usecases.SignUpUseCase
import com.example.minibankingsimulator.domain.usecases.validateRegister.ValidateEmail
import com.example.minibankingsimulator.domain.usecases.validateRegister.ValidatePassword
import com.example.minibankingsimulator.domain.usecases.validateRegister.ValidatePhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterFormState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null

)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validatePhoneNumber: ValidatePhoneNumber
) : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterScreenState>(RegisterScreenState.Initial)
    val uiState: StateFlow<RegisterScreenState> = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(RegisterFormState())
    val formState: StateFlow<RegisterFormState> = _formState.asStateFlow()


    fun updateFirstName(newName: String) {
        _formState.update { it.copy(firstName = newName) }
    }

    fun updateLastName(newName: String) {
        _formState.update { it.copy(lastName = newName) }
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        _formState.update { it.copy(phoneNumber = newPhoneNumber) }
    }

    fun updateEmail(newEmail: String) {
        _formState.update { it.copy(email = newEmail) }
    }

    fun updatePassword(newPassword: String) {
        _formState.update { it.copy(password = newPassword) }
    }

    fun register() {
        val state = _formState.value

        val emailResult = validateEmail.execute(state.email)
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(emailResult, phoneNumberResult, passwordResult).any { !it.success }

        if (hasError) {
            _formState.update { it.copy(
                emailError = emailResult.errorMessage,
                phoneNumberError = phoneNumberResult.errorMessage,
                passwordError = passwordResult.errorMessage
            ) }
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterScreenState.Loading

            val result = signUpUseCase(
                firstName = state.firstName,
                lastName = state.lastName,
                phoneNumber = state.phoneNumber,
                password = state.password,
                email = state.email
            )

            result.onSuccess {
                _uiState.value = RegisterScreenState.Success("Registration Successful")
            }.onFailure { exception ->
                _uiState.value = RegisterScreenState.Error(
                    exception.message ?: "An unknown error occurred"
                )
            }
        }
    }
}