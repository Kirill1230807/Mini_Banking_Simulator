package com.example.minibankingsimulator.presentation.screens.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.minibankingsimulator.core.ui.theme.AppTheme

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Головний екран")
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}