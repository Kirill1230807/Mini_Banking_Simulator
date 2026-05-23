package com.example.minibankingsimulator.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.minibankingsimulator.presentation.navigation.MainRoute
import com.example.minibankingsimulator.presentation.navigation.RegisterRoute

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val loginFormState by viewModel.formState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState is LoginScreenState.Success) {
            navController.navigate(MainRoute) {
                popUpTo(0)
            }
        }
    }

    LoginContent(
        navController = navController,
        uiState = uiState,
        loginFormState = loginFormState,
        onEmailChange = { viewModel.onEvent(LoginEvent.OnEmailChange(it)) },
        onPasswordChange = { viewModel.onEvent(LoginEvent.OnPasswordChange(it)) },
        onLoginClick = { viewModel.onEvent(LoginEvent.OnLoginClick) }
    )
}

@Composable
fun LoginContent(
    navController: NavController,
    uiState: LoginScreenState,
    loginFormState: LoginFormState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Авторизація",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = loginFormState.email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = uiState !is LoginScreenState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = loginFormState.password,
                onValueChange = onPasswordChange,
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = uiState !is LoginScreenState.Loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Не маєте аккаунт?")
                TextButton(onClick = { navController.navigate(RegisterRoute) }) {
                    Text("Зареєструватись")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is LoginScreenState.Loading
            ) {
                Text("Увійти")
            }

            if (uiState is LoginScreenState.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = uiState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (uiState is LoginScreenState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true, name = "Initial State")
@Composable
private fun LoginInitialPreview() {
    AppTheme {
        LoginContent(
            uiState = LoginScreenState.Initial,
            loginFormState = LoginFormState(),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun LoginLoadingPreview() {
    AppTheme {
        LoginContent(
            uiState = LoginScreenState.Loading,
            loginFormState = LoginFormState(email = "test@example.com", password = "password"),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun LoginErrorPreview() {
    AppTheme {
        LoginContent(
            uiState = LoginScreenState.Error("Невірний email або пароль"),
            loginFormState = LoginFormState(email = "wrong@email.com", password = "123"),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            navController = rememberNavController()
        )
    }
}