package com.example.minibankingsimulator.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.minibankingsimulator.R
import com.example.minibankingsimulator.presentation.screens.login.LoginScreen
import com.example.minibankingsimulator.presentation.screens.login.LoginViewModel
import com.example.minibankingsimulator.presentation.screens.mainScreen.MainScreen
import com.example.minibankingsimulator.presentation.screens.register.RegisterScreen
import com.example.minibankingsimulator.presentation.screens.register.RegisterViewModel

data class BottomNavItem<T : Any>(
    val route: T,
    val title: Int,
    val icon: Int
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    val bottomBarList = listOf(
        BottomNavItem(
            route = MainRoute,
            title = R.string.main_screen,
            icon = R.drawable.home
        ),
        BottomNavItem(
            route = SettingsRoute,
            title = R.string.settings_screen,
            icon = R.drawable.settings
        )
    )

    val showBottomBar = bottomBarList.any { item ->
        currentDestination?.hasRoute(item.route::class) == true
    }

    val registerViewModel: RegisterViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomBarList.forEach { item ->
                        val isSelected = currentDestination?.hasRoute(item.route::class) == true
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = stringResource(id = item.title)
                                )
                            },
                            label = {
                                Text(text = stringResource(id = item.title))
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = RegisterRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<RegisterRoute> {
                RegisterScreen(registerViewModel, navController)
            }
            composable<LoginRoute> {
                LoginScreen(
                    viewModel = loginViewModel,
                    navController = navController
                )
            }
            composable<MainRoute> {
                MainScreen()
            }
            composable<SettingsRoute> {
                // TODO: Add SettingsScreen
            }
        }
    }
}
