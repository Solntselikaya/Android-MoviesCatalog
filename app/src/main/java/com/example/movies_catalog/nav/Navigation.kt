package com.example.movies_catalog.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies_catalog.SignInScreen
import com.example.movies_catalog.SignUpScreen
import com.example.movies_catalog.mainScreen.MainScreen
import com.example.movies_catalog.mainScreen.NavigationItem

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SignInScreen.route) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController = navController)
        }

        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

        composable(route = Screens.MainScreen.route) {
            MainScreen(navController = navController)
        }

        composable(NavigationItem.Main.route) {
            MainScreen(navController = navController)
        }

        composable(NavigationItem.Profile.route) {
            MainScreen(navController = navController)
        }
    }
}