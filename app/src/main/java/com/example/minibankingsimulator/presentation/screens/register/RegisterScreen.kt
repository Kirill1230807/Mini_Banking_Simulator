package com.example.minibankingsimulator.presentation.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.minibankingsimulator.core.ui.theme.AppTheme
import com.example.minibankingsimulator.presentation.navigation.LoginRoute

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()

    RegisterContent(
        navController = navController,
        uiState = uiState,
        formState = formState,
        onFirstNameChange = viewModel::updateFirstName,
        onLastNameChange = viewModel::updateLastName,
        onPhoneNumberChange = viewModel::updatePhoneNumber,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onRegisterClick = viewModel::register
    )

    LaunchedEffect(uiState) {
        if (uiState is RegisterScreenState.Success) {
            navController.navigate(LoginRoute)
        }
    }
}

@Composable
fun RegisterContent(
    navController: NavController,
    uiState: RegisterScreenState,
    formState: RegisterFormState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Реєстрація",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = formState.firstName,
                onValueChange = onFirstNameChange,
                label = { Text("Ім'я") },
                isError = formState.firstNameError != null,
                supportingText = { formState.firstNameError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = formState.lastName,
                onValueChange = onLastNameChange,
                label = { Text("Прізвище") },
                isError = formState.lastNameError != null,
                supportingText = { formState.lastNameError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = formState.phoneNumber,
                onValueChange = onPhoneNumberChange,
                label = { Text("Номер телефону") },
                isError = formState.phoneNumberError != null,
                supportingText = { formState.phoneNumberError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = formState.email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                isError = formState.emailError != null,
                supportingText = { formState.emailError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = formState.password,
                onValueChange = onPasswordChange,
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                isError = formState.passwordError != null,
                supportingText = { formState.passwordError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Маєте аккаунт?")
                TextButton(onClick = { navController.navigate(LoginRoute) }) {
                    Text("Увійти")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is RegisterScreenState.Loading
            ) {
                Text("Зареєструватися")
            }

            if (uiState is RegisterScreenState.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = uiState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (uiState is RegisterScreenState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true, name = "Initial State")
@Composable
private fun RegisterInitialPreview() {
    AppTheme {
        RegisterContent(
            uiState = RegisterScreenState.Initial,
            formState = RegisterFormState(),
            onFirstNameChange = {},
            onLastNameChange = {},
            onPhoneNumberChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onRegisterClick = {},
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun RegisterLoadingPreview() {
    AppTheme {
        RegisterContent(
            uiState = RegisterScreenState.Loading,
            formState = RegisterFormState(
                firstName = "Іван",
                lastName = "Іванов",
                email = "ivan@example.com"
            ),
            onFirstNameChange = {},
            onLastNameChange = {},
            onPhoneNumberChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onRegisterClick = {},
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun RegisterErrorPreview() {
    AppTheme {
        RegisterContent(
            uiState = RegisterScreenState.Error("Користувач з таким Email вже існує"),
            formState = RegisterFormState(
                firstName = "Іван",
                lastName = "Іванов",
                email = "ivan@example.com"
            ),
            onFirstNameChange = {},
            onLastNameChange = {},
            onPhoneNumberChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onRegisterClick = {},
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true, name = "Validation Errors")
@Composable
private fun RegisterValidationErrorsPreview() {
    AppTheme {
        RegisterContent(
            uiState = RegisterScreenState.Initial,
            formState = RegisterFormState(
                email = "invalid-email",
                emailError = "Невірний формат email",
                password = "123",
                passwordError = "Пароль надто короткий"
            ),
            onFirstNameChange = {},
            onLastNameChange = {},
            onPhoneNumberChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onRegisterClick = {},
            navController = rememberNavController()
        )
    }
}
